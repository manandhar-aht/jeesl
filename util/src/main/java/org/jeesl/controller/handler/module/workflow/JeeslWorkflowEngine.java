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
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionHandler;
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
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateType;
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
							AX extends JeeslWorkflowContext<AX,L,D,?>,
							WP extends JeeslWorkflowProcess<L,D,AX>,
							AS extends JeeslWorkflowStage<L,D,WP,AST>,
							AST extends JeeslWorkflowStageType<AST,?,?,?>,
							WSP extends JeeslWorkflowStagePermission<AS,WPT,WML,SR>,
							WPT extends JeeslWorkflowPermissionType<WPT,L,D,?>,
							WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
							WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
							ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
							WC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
							AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
							AB extends JeeslWorkflowBot<AB,L,D,?>,
							AO extends EjbWithId,
							MT extends JeeslIoTemplate<L,D,?,?,MD,?>,
							MC extends JeeslTemplateType<L,D,MC,?>,
							MD extends JeeslIoTemplateDefinition<D,MC,MT>,
							SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
							RE extends JeeslRevisionEntity<L,D,?,?,RA>,
							RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
							AL extends JeeslApprovalLink<AW,RE>,
							AW extends JeeslApprovalWorkflow<WP,AS,WY>,
							WY extends JeeslApprovalActivity<WT,AW,USER>,
							USER extends JeeslUser<SR>
							>
				implements JeeslJsfWorkflowHandler
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowEngine.class);
	
	private boolean debugOnInfo; protected void setDebugOnInfo(boolean debugOnInfo){this.debugOnInfo=debugOnInfo;}
	
	private final JeeslWorkflowFacade<L,D,LOC,AX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,USER> fWorkflow;
	
	private final WorkflowFactoryBuilder<L,D,AX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,USER> fbWorkflow;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision;
	
	private JeeslJsfSecurityHandler<SR,?,?,?,?,USER> security;
	private final JeeslWorkflowCommunicator<L,D,LOC,AX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,AA,AB,AO,MT,MC,MD,SR,RE,RA,AW,WY,USER> communicator;
	private final JeeslWorkflowActionHandler<AA,AB,AO,RE,RA,AW> actionHandler;
	
	private final Comparator<WY> cpActivity;
	
	private final Map<WY,byte[]> mapSignature; public Map<WY, byte[]> getMapSignature() {return mapSignature;}
	
	private final List<WY> activities; public List<WY> getActivities() {return activities;}
	private final List<WT> transitions; public List<WT> getTransitions() {return transitions;}
	private final List<AA> actions; public List<AA> getActions() {return actions;}
	private final List<WC> communications; public List<WC> getCommunications() {return communications;}
	
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
	
	public JeeslWorkflowEngine(WorkflowFactoryBuilder<L,D,AX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,USER> fbWorkflow,
								IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision,
								JeeslWorkflowFacade<L,D,LOC,AX,WP,AS,AST,WSP,WPT,WML,WT,ATT,WC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,USER> fWorkflow,
								JeeslWorkflowMessageHandler<WC,SR,RE,MT,MC,MD,AW,WY,USER> recipientResolver,
								JeeslWorkflowActionHandler<AA,AB,AO,RE,RA,AW> actionHandler)
	{
		this.fbWorkflow=fbWorkflow;
		this.fbRevision=fbRevision;
		
		this.fWorkflow=fWorkflow;
		
		debugOnInfo = false;
		
		cpActivity = new RecordComparator<WY>();
		
		mapSignature = new HashMap<>();
		
		transitions = new ArrayList<>();
		activities = new ArrayList<>();
		actions = new ArrayList<>();
		communications = new ArrayList<>();
		
		communicator = new JeeslWorkflowCommunicator<>(recipientResolver);
		communicator.setDebugOnInfo(debugOnInfo);
		
		this.actionHandler=actionHandler;
		actionHandler.setDebugOnInfo(debugOnInfo);
	}
	
	public void clearSignature(){reset(false,false,true,false);}
	private void reset(boolean rTransitions, boolean rTransition, boolean rSignature, boolean rWorkflow)
	{
		if(rTransitions) {transitions.clear();}
		if(rTransition) {transition = null;}
		if(rSignature) {screenSignature = null;}
		if(rWorkflow) {workflow=null;link=null;}
	}
	
	public void addWorkflow(JeeslJsfSecurityHandler<SR,?,?,?,?,USER> security, USER user, JeeslWithWorkflow<AW> ejb)
	{
		this.security = security;
		this.user=user;
		this.entity = ejb;
		reset(true,true,true,true);
		workflow = fbWorkflow.ejbWorkflow().build(process);
	
		WT transition = fWorkflow.fTransitionBegin(process);
		workflow.setCurrentStage(transition.getDestination());
		
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
		entity=ejb;
		workflow = fWorkflow.save(workflow);
		link.setWorkflow(workflow);
		link.setRefId(ejb.getId());
		link = fWorkflow.save(link);
		
		if(debugOnInfo) {logger.info("Saved: Workflow and Link");}
	}
	
	public <W extends JeeslWithWorkflow<AW>> void selectEntity(JeeslJsfSecurityHandler<SR,?,?,?,?,USER> security, USER user, W ejb) throws UtilsNotFoundException
	{
		this.security=security;
		this.user=user;
		this.entity = ejb;
		reset(true,true,true,true);
		if(debugOnInfo) {logger.info("Select: Workflow and Link");}
		
		link = fWorkflow.fLink(process,ejb);
		workflow = link.getWorkflow();
		reloadWorkflow(false);
	}
	
	public void reloadWorkflow() {reloadWorkflow(true);}
	public void reloadWorkflow(boolean reloadEntity)
	{
		reset(true,true,true,false);
		if(reloadEntity) {workflow = fWorkflow.find(fbWorkflow.getClassWorkflow(),workflow);}
		
		List<WSP> availablePermissions = fWorkflow.allForParent(fbWorkflow.getClassPermission(), workflow.getCurrentStage());
		if(debugOnInfo) {logger.info("Checking "+availablePermissions.size()+" "+fbWorkflow.getClassPermission().getSimpleName());}
		boolean hasResponsibleRole = false;
		allowEntityModifications  = false;
		for(WSP wsp : availablePermissions)
		{
			boolean wspIsResponsible = wsp.getType().getCode().contentEquals(JeeslWorkflowPermissionType.Code.responsible.toString());
			boolean userHasRole = security.hasRole(wsp.getRole());
			boolean wspIsFullAllow = wsp.getModificationLevel().getCode().contentEquals(JeeslWorkflowModificationLevel.Code.full.toString());
			hasResponsibleRole = wspIsResponsible && userHasRole;
			allowEntityModifications = wspIsFullAllow && userHasRole;
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
	
	public void prepareTransition(WT t, boolean autoPerform) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException
	{
		transition = fWorkflow.find(fbWorkflow.getClassTransition(),t);
		logger.info("prepareTransition for "+transition.toString());
		
		remark="";
		screenSignature=null;
		
		actions.clear();actions.addAll(fWorkflow.allForParent(fbWorkflow.getClassAction(),transition));
		communications.clear();communications.addAll(fWorkflow.allForParent(fbWorkflow.getClassCommunication(),transition));
		if(debugOnInfo) {logger.info("Prepared "+fbWorkflow.getClassTransition().getSimpleName()+" to "+transition.toString()+": "+JeeslWorkflowCommunication.class.getSimpleName()+":"+communications.size()+" "+JeeslWorkflowAction.class.getSimpleName()+":"+actions.size());}
		if(autoPerform) {performTransition();}
	}
	
	public void performTransition() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException
	{
		if(debugOnInfo) {logger.info("Perform "+fbWorkflow.getClassTransition().getSimpleName()+" to "+transition.toString());}
		
		workflow.setCurrentStage(transition.getDestination());
		workflow = fWorkflow.save(workflow);
		
		actionHandler.perform(entity,actions);
		
		activity = fbWorkflow.ejbActivity().build(workflow,transition,user);
		activity.setRemark(remark);
		activity.setScreenSignature(screenSignature);
		activity = fWorkflow.save(activity);
		
		communicator.build(activity,entity,communications);
		
		remark = null;
		screenSignature = null;
		transition=null;
		activity = null;
		
		reloadWorkflow();
	}
}