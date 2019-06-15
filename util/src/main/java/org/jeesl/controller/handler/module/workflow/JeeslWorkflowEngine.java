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
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
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
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.RecordComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslWorkflowEngine <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
							AX extends JeeslWorkflowContext<AX,L,D,?>,
							AP extends JeeslWorkflowProcess<L,D,AX>,
							AS extends JeeslWorkflowStage<L,D,AP,AST>,
							AST extends JeeslWorkflowStageType<AST,?,?,?>,
							ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
							APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
							WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
							WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
							ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
							AC extends JeeslWorkflowCommunication<WT,MT,SR>,
							AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
							AB extends JeeslWorkflowBot<AB,L,D,?>,
							AO extends EjbWithId,
							MT extends JeeslIoTemplate<L,D,?,?,MD,?>,
							MD extends JeeslIoTemplateDefinition<D,?,MT>,
							SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
							RE extends JeeslRevisionEntity<L,D,?,?,RA>,
							RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
							AL extends JeeslApprovalLink<AW,RE>,
							AW extends JeeslApprovalWorkflow<AP,AS,WY>,
							WY extends JeeslApprovalActivity<WT,AW,USER>,
							USER extends JeeslUser<SR>
							>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowEngine.class);
	
	private final static boolean debugOnInfo = true;
	
	private final JeeslWorkflowFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,WY,USER> fWorkflow;
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,WY,USER> fbWorkflow;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision;
		
	private final JeeslWorkflowCommunicator<L,D,LOC,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,MD,SR,RE,RA,AW,WY,USER> communicator;
	private final JeeslWorkflowActionHandler<AA,AB,AO,RE,RA,AW> actionHandler;
	
	private final Comparator<WY> cpActivity;
	
	private final Map<WY,byte[]> mapSignature; public Map<WY, byte[]> getMapSignature() {return mapSignature;}
	
	private final List<WY> activities; public List<WY> getActivities() {return activities;}
	private final List<WT> transitions; public List<WT> getTransitions() {return transitions;}
	private final List<AA> actions; public List<AA> getActions() {return actions;}
	private final List<AC> communications; public List<AC> getCommunications() {return communications;}
	
	private USER user;
	private JeeslWithWorkflow<AW> entity;
	protected AP process; public AP getProcess() {return process;} protected void setProcess(AP process) {this.process = process;}
	private AL link; public AL getLink() {return link;} public void setLink(AL link) {this.link = link;}
	private AW workflow; public AW getWorkflow() {return workflow;} public void setWorkflow(AW workflow) {this.workflow = workflow;}
	private WY activity; public WY getActivity() {return activity;} public void setActivity(WY activity) {this.activity = activity;}
	private WT transition; public WT getTransition() {return transition;}
	private String remark; public String getRemark() {return remark;} public void setRemark(String remark) {this.remark = remark;}
	private String screenSignature; public String getScreenSignature() {return screenSignature;}public void setScreenSignature(String screenSignature) {this.screenSignature = screenSignature;}
	
	public JeeslWorkflowEngine(WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,WY,USER> fbWorkflow,
								IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision,
								JeeslWorkflowFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,WY,USER> fWorkflow,
								JeeslWorkflowMessageHandler<SR,MT,MD,AW,USER> recipientResolver,
								JeeslWorkflowActionHandler<AA,AB,AO,RE,RA,AW> actionHandler)
	{
		this.fbWorkflow=fbWorkflow;
		this.fbRevision=fbRevision;
		
		this.fWorkflow=fWorkflow;
		
		cpActivity = new RecordComparator();
		
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
	
	public void clearSignature(){reset(false,false,true);}
	private void reset(boolean rTransitions, boolean rTransition, boolean rSignature)
	{
		if(rTransitions) {transitions.clear();}
		if(rTransition) {transition = null;}
		if(rSignature) {screenSignature = null;}
	}
	
	public void buildWorkflow(USER user, JeeslWithWorkflow<AW> ejb)
	{
		this.entity = ejb;
		this.user=user;
		reset(true,true,true);
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
	
	public <W extends JeeslWithWorkflow<AW>> void selectEntity(USER user, W ejb) throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info("Select: Workflow and Link");}
		entity = ejb;
		this.user=user;
		
		link = fWorkflow.fLink(process,ejb);
		workflow = link.getWorkflow();
	}
	
	public void reloadWorkflow()
	{
		reset(true,true,true);
		workflow = fWorkflow.find(fbWorkflow.getClassWorkflow(),workflow);
		
		transitions.addAll(fWorkflow.allForParent(fbWorkflow.getClassTransition(), workflow.getCurrentStage()));
		
		activities.clear();
		activities.addAll(fWorkflow.allForParent(fbWorkflow.getClassActivity(), workflow));
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
		
		if(debugOnInfo) {logger.info("reloadWorkflow: "+transitions.size()+" "+fbWorkflow.getClassTransition().getSimpleName());}
	}
	
	public void prepareTransition(WT t, boolean autoPerform) throws UtilsConstraintViolationException, UtilsLockingException
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
	
	public void performTransition() throws UtilsConstraintViolationException, UtilsLockingException
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
		
		reset(true,true,true);
	}
	
	public void loadStages()
	{
		
	}
}