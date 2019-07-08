package org.jeesl.controller.handler.module.workflow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.png.SignatureTranscoder;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryHandler;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionsHandler;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowMessageHandler;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransitionType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.web.JeeslJsfSecurityHandler;
import org.jeesl.interfaces.web.JeeslJsfWorkflowHandler;
import org.jeesl.util.comparator.ejb.RecordComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslWorkflowEngine <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
							WX extends JeeslWorkflowContext<WX,L,D,?>,
							WP extends JeeslWorkflowProcess<L,D,WX>,
							AS extends JeeslWorkflowStage<L,D,WP,AST,?>,
							AST extends JeeslWorkflowStageType<AST,?,?,?>,
							WSP extends JeeslWorkflowStagePermission<AS,WPT,WML,SR>,
							WPT extends JeeslWorkflowPermissionType<WPT,L,D,?>,
							WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
							WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR,?>,
							ATT extends JeeslWorkflowTransitionType<ATT,L,D,?>,
							WC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
							WA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
							AB extends JeeslWorkflowBot<AB,L,D,?>,
							AO extends EjbWithId,
							MT extends JeeslIoTemplate<L,D,?,?,MD,?>,
							MC extends JeeslTemplateChannel<L,D,MC,?>,
							MD extends JeeslIoTemplateDefinition<D,MC,MT>,
							SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
							RE extends JeeslRevisionEntity<L,D,?,?,RA>,
							RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
							AL extends JeeslApprovalLink<AW,RE>,
							AW extends JeeslApprovalWorkflow<WP,AS,WY>,
							WY extends JeeslApprovalActivity<WT,AW,FRC,USER>,
							FRC extends JeeslFileContainer<?,?>,
							WCS extends JeeslConstraint<L,D,?,?,?,?,?,?>,
							USER extends JeeslUser<SR>
							>
				implements JeeslJsfWorkflowHandler
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowEngine.class);
	
	private boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo){this.debugOnInfo=debugOnInfo;if(actionsHandler!=null) {actionsHandler.setDebugOnInfo(debugOnInfo);}}
	private boolean allowTransitions; public boolean isAllowTransitions() {return allowTransitions;} public void setAllowTransitions(boolean allowTransitions) {this.allowTransitions = allowTransitions;}
	
	private final JeeslWorkflowFacade<L,D,LOC,WX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow;
	
	private final WorkflowFactoryBuilder<L,D,WX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision;
	
	private JeeslJsfSecurityHandler<SR,?,?,?,?,USER> security;
	private JeeslFileRepositoryHandler<?,FRC,?> frh; public JeeslFileRepositoryHandler<?, FRC, ?> getFrh() {return frh;}
	private final JeeslWorkflowCommunicator<L,D,LOC,WX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,WA,AB,AO,MT,MC,MD,SR,RE,RA,AW,WY,FRC,USER> communicator;
	private final JeeslWorkflowActionsHandler<WT,WA,AB,AO,RE,RA,AW,WCS> actionsHandler;
	
	private final Comparator<WY> cpActivity;
	
	private final Map<JeeslWithWorkflow<AW>,AW> mapWorkflow; public Map<JeeslWithWorkflow<AW>, AW> getMapWorkflow() {return mapWorkflow;}
	private final Map<WY,byte[]> mapSignature; public Map<WY, byte[]> getMapSignature() {return mapSignature;}
	
	private final List<WY> activities; public List<WY> getActivities() {return activities;}
	private final List<WT> transitions; public List<WT> getTransitions() {return transitions;}
	private final List<WA> actions; public List<WA> getActions() {return actions;}
	private final List<WC> communications; public List<WC> getCommunications() {return communications;}
	private final List<WCS> constraints; public List<WCS> getConstraints() {return constraints;}

	private USER user;
	private JeeslWithWorkflow<AW> entity;

	protected WP process; public WP getProcess() {return process;} protected void setProcess(WP process) {this.process = process;}
	private AL link; public AL getLink() {return link;} public void setLink(AL link) {this.link = link;}
	private AW workflow; public AW getWorkflow() {return workflow;} public void setWorkflow(AW workflow) {this.workflow = workflow;}
	private WY activity; public WY getActivity() {return activity;} public void setActivity(WY activity) {this.activity = activity;}
	private WT transition; public WT getTransition() {return transition;}
	private String remark; public String getRemark() {return remark;} public void setRemark(String remark) {this.remark = remark;}
	private String screenSignature; public String getScreenSignature() {return screenSignature;}public void setScreenSignature(String screenSignature) {this.screenSignature = screenSignature;}
	
	private boolean historyWithSignature; public boolean isHistoryWithSignature() {return historyWithSignature;}
	private boolean allowEntityModifications; @Override public boolean isAllowEntityModifications() {return allowEntityModifications;}
	
	public JeeslWorkflowEngine(WorkflowFactoryBuilder<L,D,WX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
								IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision,
								JeeslWorkflowFacade<L,D,LOC,WX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow,
								JeeslWorkflowMessageHandler<WC,SR,RE,MT,MC,MD,AW,WY,USER> recipientResolver,
								JeeslWorkflowActionsHandler<WT,WA,AB,AO,RE,RA,AW,WCS> actionHandler,
								JeeslFileRepositoryHandler<?,FRC,?> frh)
	{
		this.fbWorkflow=fbWorkflow;
		this.fbRevision=fbRevision;
		
		this.fWorkflow=fWorkflow;
		this.frh=frh;
		
		debugOnInfo = false;
		allowTransitions = true;
		
		cpActivity = new RecordComparator<WY>();
		
		mapWorkflow = new HashMap<>();
		mapSignature = new HashMap<>();
		
		transitions = new ArrayList<>();
		activities = new ArrayList<>();
		actions = new ArrayList<>();
		communications = new ArrayList<>();
		constraints = new ArrayList<>();
		
		communicator = new JeeslWorkflowCommunicator<>(recipientResolver);
		communicator.setDebugOnInfo(debugOnInfo);
		
		this.actionsHandler=actionHandler;
		actionHandler.setDebugOnInfo(debugOnInfo);
	}
	
	public void reset() {reset(true,true,true,true,true);}
	public void clearSignature(){reset(false,false,true,false,false);}
	private void reset(boolean rTransitions, boolean rTransition, boolean rSignature, boolean rWorkflow, boolean rFrh)
	{
		if(rTransitions) {transitions.clear();}
		if(rTransition) {transition = null;}
		if(rSignature) {screenSignature = null;}
		if(rWorkflow) {workflow=null;link=null;activities.clear();}
//		if(rFrh) {frh.reset();}
	}
	
	public void addWorkflow(JeeslJsfSecurityHandler<SR,?,?,?,?,USER> security, USER user, JeeslWithWorkflow<AW> ejb)
	{
		this.security = security;
		this.user=user;
		this.entity = ejb;
		reset(true,true,true,true,true);
		workflow = fbWorkflow.ejbWorkflow().build(process);
	
		WT transition = fWorkflow.fTransitionBegin(process);
		workflow.setCurrentStage(transition.getDestination());
		if(debugOnInfo) {logger.info("Using transition: "+transition.toString());}
		
		WY activity = fbWorkflow.ejbActivity().build(workflow,transition,user);
		workflow.getActivities().add(activity);
		
		RE entity = null;
		try {entity = fWorkflow.fByCode(fbRevision.getClassEntity(),ejb.getClass().getName());}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		
		link = fbWorkflow.ejbLink().build(entity,workflow,ejb);
		if(debugOnInfo) {logger.info("Build: Workflow and Link");}
		reloadWorkflow(false);
	}
	
	public <W extends JeeslWithWorkflow<AW>> void saveWorkflow(JeeslWithWorkflow<AW> ejb) throws UtilsConstraintViolationException, UtilsLockingException
	{
		this.entity=ejb;
		if(workflow!=null)
		{
			workflow = fWorkflow.save(workflow);
			link.setWorkflow(workflow);
			link.setRefId(ejb.getId());
			link = fWorkflow.save(link);
			
			if(debugOnInfo) {logger.info("Saved: Workflow and Link");}
		}
	}
	
	public <W extends JeeslWithWorkflow<AW>> void selectEntity(JeeslJsfSecurityHandler<SR,?,?,?,?,USER> security, USER user, W ejb) throws UtilsNotFoundException
	{
		this.security=security;
		this.user=user;
		this.entity = ejb;
		reset(true,true,true,true,true);
		
		link = fWorkflow.fLink(process,ejb);
		workflow = link.getWorkflow();
		if(debugOnInfo) {logger.info("Select: Workflow and Link");}
		reloadWorkflow(false);
	}
	
	public <W extends JeeslWithWorkflow<AW>> void loadWorkflows(List<W> ejbs)
	{
		mapWorkflow.clear();
		for(W  w : ejbs)
		{
			try
			{
				AL link = fWorkflow.fLink(process,w);
				mapWorkflow.put(w,link.getWorkflow());
			}
			catch (UtilsNotFoundException e) {}
		}
	}
	
	public void reloadWorkflow() {reloadWorkflow(true);}
	public void reloadWorkflow(boolean ejbLoadWorkflow)
	{
		reset(true,true,true,false,true);
		if(workflow==null) {return;}
		if(ejbLoadWorkflow) {workflow = fWorkflow.find(fbWorkflow.getClassWorkflow(),workflow);}
		
		constraints.clear();
		
		List<WSP> availablePermissions = fWorkflow.allForParent(fbWorkflow.getClassPermission(), workflow.getCurrentStage());
		if(debugOnInfo) {logger.info("Checking "+availablePermissions.size()+" "+fbWorkflow.getClassPermission().getSimpleName());}
		boolean hasResponsibleRole = false;
		allowEntityModifications  = false;
		for(WSP wsp : availablePermissions)
		{
			
			boolean wspIsResponsible = wsp.getType().getCode().contentEquals(JeeslWorkflowPermissionType.Code.responsible.toString());
			boolean userHasRole = security.hasRole(wsp.getRole());
			boolean wspIsFullAllow = wsp.getModificationLevel().getCode().contentEquals(JeeslWorkflowModificationLevel.Code.full.toString());
			
			if(wspIsResponsible && userHasRole) {hasResponsibleRole=true;}
			if(wspIsFullAllow && userHasRole) {allowEntityModifications=true;}
			if(debugOnInfo) {logger.info("\t"+wsp.getPosition()+" "+wsp.getRole().getCode()+":"+userHasRole+" "+JeeslWorkflowPermissionType.Code.responsible+":"+wspIsResponsible+" "+JeeslWorkflowModificationLevel.Code.full+":"+wspIsFullAllow);}
		}
		
		if(EjbIdFactory.isSaved(entity))
		{
			List<WT> availableTransitions = fWorkflow.allForParent(fbWorkflow.getClassTransition(), workflow.getCurrentStage());
			if(debugOnInfo) {logger.info("Checking "+availableTransitions.size()+" "+fbWorkflow.getClassTransition().getSimpleName());}
			for(WT t : availableTransitions)
			{
				StringBuilder sb = null;
				if(debugOnInfo){sb = new StringBuilder();sb.append("\tChecking "+fbWorkflow.getClassTransition().getSimpleName()+" "+t.getPosition()+" visible:"+t.isVisible());}
				if(t.isVisible())
				{
					
					if(t.getRole()==null)
					{
						if(debugOnInfo) {sb.append(" has no special role, adding if responsible?"+hasResponsibleRole);}
						if(hasResponsibleRole) {transitions.add(t);}
					}
					else
					{
						boolean hasSpecialRole = security.hasRole(t.getRole());
						if(debugOnInfo) {sb.append(" has special role ").append(t.getRole().getCode()).append(" user:").append(hasSpecialRole);}
						if(hasSpecialRole) {transitions.add(t);}
					}
				}
				if(debugOnInfo) {logger.info(sb.toString());}
			}
		}
		else if(debugOnInfo) {logger.info("Not Checking Transitions because etiher hasResponsibleRole:"+hasResponsibleRole+" or isSaved"+EjbIdFactory.isSaved(entity));}
		
		activities.clear();
		if(EjbIdFactory.isSaved(workflow)){activities.addAll(fWorkflow.allForParent(fbWorkflow.getClassActivity(), workflow));}
		
		Collections.sort(activities,cpActivity);
		Collections.reverse(activities);
		
		mapSignature.clear();
		for(WY a : activities)
		{
			if(a.getScreenSignature()!=null)
			{
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				try
				{
					SignatureTranscoder.generateSignature(a.getScreenSignature(),os,4);
					mapSignature.put(a,os.toByteArray());
				}
				catch (IOException e) {e.printStackTrace();}
			}
		}
		historyWithSignature = !mapSignature.isEmpty();
		
		if(debugOnInfo) {logger.info("reloadWorkflow: "+transitions.size()+" "+fbWorkflow.getClassTransition().getSimpleName());}
	}
	
	public void prepareTransition(WT t, boolean autoPerform) throws UtilsConstraintViolationException, UtilsLockingException, UtilsProcessingException, JeeslWorkflowException, UtilsNotFoundException
	{
		transition = fWorkflow.find(fbWorkflow.getClassTransition(),t);
		if(debugOnInfo) {logger.info("prepareTransition for "+transition.toString());}
		
		remark="";
		screenSignature=null;
		
		actions.clear();actions.addAll(fWorkflow.allForParent(fbWorkflow.getClassAction(),transition));
		communications.clear();communications.addAll(fWorkflow.allForParent(fbWorkflow.getClassCommunication(),transition));
		
		constraints.clear();
		actionsHandler.checkPreconditions(constraints,entity,actions);
		
		if(debugOnInfo) {logger.info("Prepared "+fbWorkflow.getClassTransition().getSimpleName()+" to "+transition.toString()+": "+JeeslWorkflowCommunication.class.getSimpleName()+":"+communications.size()+" "+JeeslWorkflowAction.class.getSimpleName()+":"+actions.size());}
		if(autoPerform && constraints.isEmpty()) {performTransition();}
	}
	
	public void performTransition() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException
	{
		if(debugOnInfo) {logger.info("Perform "+fbWorkflow.getClassTransition().getSimpleName()+" to "+transition.getDestination().getCode());}
		
		constraints.clear();
		actionsHandler.checkPreconditions(constraints,entity,actions);
		actionsHandler.checkRemark(constraints,transition,remark);
		
		if(!constraints.isEmpty())
		{
			if(debugOnInfo) {logger.info("PreconditionCheck failed. Aborting.");}
//			actionHandler.abort(entity);
			return;
		}
		
		try
		{
			actionsHandler.perform(entity,actions);
			
			workflow.setCurrentStage(transition.getDestination());
			workflow = fWorkflow.save(workflow);
			
			activity = fbWorkflow.ejbActivity().build(workflow,transition,user);
			activity.setRemark(remark);
			activity.setScreenSignature(screenSignature);
			activity = fWorkflow.save(activity);
			
			communicator.build(activity,entity,communications);
			
			remark = null;
			screenSignature = null;
			transition=null;
			activity = null;
			
			reloadWorkflow(true);
		}
		catch (JeeslWorkflowException e)
		{
			logger.warn(e.getMessage());
			actionsHandler.abort(entity);
		}
	}
}