package org.jeesl.controller.handler.module.workflow;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionHandler;
import org.jeesl.interfaces.controller.processor.WorkflowRecipientResolver;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
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
							AX extends JeeslApprovalContext<AX,L,D,?>,
							AP extends JeeslApprovalProcess<L,D,AX>,
							AS extends JeeslApprovalStage<L,D,AP,AST>,
							AST extends JeeslApprovalStageType<AST,?,?,?>,
							ASP extends JeeslApprovalStagePermission<AS,APT,SR>,
							APT extends JeeslApprovalPermissionType<APT,L,D,?>,
							AT extends JeeslApprovalTransition<L,D,AS,ATT>,
							ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
							AC extends JeeslApprovalCommunication<AT,MT,SR>,
							AA extends JeeslApprovalAction<AT,AB,AO,RE,RA>,
							AB extends JeeslApprovalBot<AB,L,D,?>,
							AO extends EjbWithId,
							MT extends JeeslIoTemplate<L,D,?,?,?,?>,
							SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
							RE extends JeeslRevisionEntity<L,D,?,?,RA>,
							RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
							AL extends JeeslApprovalLink<AW,RE>,
							AW extends JeeslApprovalWorkflow<AP,AS,AY>,
							AY extends JeeslApprovalActivity<AT,AW,USER>,
							USER extends JeeslUser<SR>
							>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowEngine.class);
	
	private final static boolean debugOnInfo = true;
	
	private final JeeslApprovalFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fApproval;
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision;
		
	private final JeeslWorkflowCommunicator<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AW,AY,USER> communicator;
	private final JeeslWorkflowActionHandler<AA,AB,AO,RE,RA,AW> actionHandler;
	
	private final List<AY> activities; public List<AY> getActivities() {return activities;} private final List<AT> transitions; public List<AT> getTransitions() {return transitions;}
	private final List<AA> actions; public List<AA> getActions() {return actions;}
	private final List<AC> communications; public List<AC> getCommunications() {return communications;}
	
	private USER user;
	private JeeslWithWorkflow<AW> entity;
	protected AP process; public AP getProcess() {return process;} protected void setProcess(AP process) {this.process = process;}
	private AL link; public AL getLink() {return link;} public void setLink(AL link) {this.link = link;}
	private AW workflow; public AW getWorkflow() {return workflow;} public void setWorkflow(AW workflow) {this.workflow = workflow;}
	private AY activity; public AY getActivity() {return activity;} public void setActivity(AY activity) {this.activity = activity;}
	private AT transition; public AT getTransition() {return transition;}
	private String remark; public String getRemark() {return remark;} public void setRemark(String remark) {this.remark = remark;}
	
	public JeeslWorkflowEngine(WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval,
								IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision,
								JeeslApprovalFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fApproval,
								WorkflowRecipientResolver<SR,AW,USER> recipientResolver,
								JeeslWorkflowActionHandler<AA,AB,AO,RE,RA,AW> actionHandler)
	{
		this.fbApproval=fbApproval;
		this.fbRevision=fbRevision;
		
		this.fApproval=fApproval;
		
		transitions = new ArrayList<>();
		activities = new ArrayList<>();
		actions = new ArrayList<>();
		communications = new ArrayList<>();
		
		communicator = new JeeslWorkflowCommunicator<>(recipientResolver);
		communicator.setDebugOnInfo(debugOnInfo);
		
		this.actionHandler=actionHandler;
		actionHandler.setDebugOnInfo(debugOnInfo);
	}
	
	public void buildWorkflow(USER user, JeeslWithWorkflow<AW> ejb)
	{
		entity = ejb;
		this.user=user;
		workflow = fbApproval.ejbWorkflow().build(process);
	
		AT transition = fApproval.fTransitionBegin(process);
		workflow.setCurrentStage(transition.getDestination());
		
		AY activity = fbApproval.ejbActivity().build(workflow,transition,user);
		workflow.getActivities().add(activity);
		
		RE entity = null;
		try {entity = fApproval.fByCode(fbRevision.getClassEntity(),ejb.getClass().getName());}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		
		link = fbApproval.ejbLink().build(entity,workflow,ejb);
		if(debugOnInfo) {logger.info("Build: Workflow and Link");}
	}
	
	public <W extends JeeslWithWorkflow<AW>> void saveWorkflow(JeeslWithWorkflow<AW> ejb) throws UtilsConstraintViolationException, UtilsLockingException
	{
		entity=ejb;
		workflow = fApproval.save(workflow);
		link.setWorkflow(workflow);
		link.setRefId(ejb.getId());
		link = fApproval.save(link);
		
		if(debugOnInfo) {logger.info("Saved: Workflow and Link");}
		
	}
	
	public <W extends JeeslWithWorkflow<AW>> void selectEntity(USER user, W ejb) throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info("Select: Workflow and Link");}
		entity = ejb;
		this.user=user;
		
		link = fApproval.fLink(process, ejb);
		workflow = link.getWorkflow();
	}
	
	public void reloadWorkflow()
	{
		workflow = fApproval.find(fbApproval.getClassWorkflow(),workflow);
		
		transitions.clear();
		transitions.addAll(fApproval.allForParent(fbApproval.getClassTransition(), workflow.getCurrentStage()));
		
		activities.clear();
		activities.addAll(fApproval.allForParent(fbApproval.getClassActivity(), workflow));
		
		if(debugOnInfo) {logger.info("reloadWorkflow: "+transitions.size());}
	}
	
	public void prepareTransition(AT t)
	{
		transition = fApproval.find(fbApproval.getClassTransition(), t);
		logger.info("prepareTransition for "+transition.toString());
		
		actions.clear();actions.addAll(fApproval.allForParent(fbApproval.getClassAction(),transition));
		communications.clear();communications.addAll(fApproval.allForParent(fbApproval.getClassCommunication(),transition));
		if(debugOnInfo) {logger.info("Prepared "+fbApproval.getClassTransition().getSimpleName()+" to "+transition.toString()+": "+JeeslApprovalCommunication.class.getSimpleName()+":"+communications.size()+" "+JeeslApprovalAction.class.getSimpleName()+":"+actions.size());}
	}
	
	public void performTransition() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info("Perform "+fbApproval.getClassTransition()+" to "+transition.toString());}
		
		workflow.setCurrentStage(transition.getDestination());
		workflow = fApproval.save(workflow);
		
		actionHandler.perform(entity,actions);
		
		activity = fbApproval.ejbActivity().build(workflow,transition,user);
		activity.setRemark(remark);
		activity = fApproval.save(activity);
		
		communicator.build(entity,communications);
		
		remark = null;
		transition=null;
		activity = null;
		
		reloadWorkflow();
	}
}