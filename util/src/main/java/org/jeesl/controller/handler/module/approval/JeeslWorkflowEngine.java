package org.jeesl.controller.handler.module.approval;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalPermissionType;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStagePermission;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.approval.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslWorkflowEngine <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
							AX extends JeeslApprovalContext<AX,L,D,?>,
							AP extends JeeslApprovalProcess<L,D,AX>,
							AS extends JeeslApprovalStage<L,D,AP>,
							ASP extends JeeslApprovalStagePermission<AS,APT,SR>,
							APT extends JeeslApprovalPermissionType<APT,L,D,?>,
							AT extends JeeslApprovalTransition<L,D,AS,ATT>,
							ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
							AC extends JeeslApprovalCommunication<AT,MT,SR>,
							AA extends JeeslApprovalAction<AT,AB,AO,RE,RA>,
							AB extends JeeslApprovalBot<AB,L,D,?>,
							AO extends EjbWithId,
							MT extends JeeslIoTemplate<L,D,?,?,?,?>,
							SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
							RE extends JeeslRevisionEntity<L,D,?,?,RA>,
							RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
							AW extends JeeslApprovalWorkflow<AP,AS,AY>,
							AY extends JeeslApprovalActivity<AT,AW>
							>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowEngine.class);
	
	private final static boolean debugOnInfo = true;
	
	private final JeeslApprovalFacade<L,D,LOC,AX,AP,AS,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AW,AY> fApproval;
	private final ApprovalFactoryBuilder<L,D,AX,AP,AS,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AW,AY> fbApproval;
	
	protected AP process; public AP getProcess() {return process;} protected void setProcess(AP process) {this.process = process;}
	private AW workflow; public AW getWorkflow() {return workflow;} public void setWorkflow(AW workflow) {this.workflow = workflow;}
	
	private final List<AY> activities;
	public List<AY> getActivities() {
		return activities;
	}
	private final List<AT> transitions; public List<AT> getTransitions() {return transitions;}
	
	private AY activity;
	
	public AY getActivity() {
		return activity;
	}
	public void setActivity(AY activity) {
		this.activity = activity;
	}
	public JeeslWorkflowEngine(ApprovalFactoryBuilder<L,D,AX,AP,AS,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AW,AY> fbApproval,
								JeeslApprovalFacade<L,D,LOC,AX,AP,AS,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AW,AY> fApproval)
	{
		this.fbApproval=fbApproval;
		this.fApproval=fApproval;
		
		transitions = new ArrayList<>();
		activities = new ArrayList<>();
	}
	
	public void create(JeeslWithWorkflow<AW> ejb)
	{
		workflow = fbApproval.ejbWorkflow().build(process);
	
		AT transition = fApproval.fTransitionBegin(process);
		workflow.setCurrentStage(transition.getDestination());
		
		AY activity = fbApproval.ejbActivity().build(workflow,transition);
		workflow.getActivities().add(activity);
		
		ejb.setWorkflow(workflow);
		logger.info("pre-Save "+ejb);
	}
	
	public <W extends JeeslWithWorkflow<AW>> void init(Class<W> cWith, W ejb)
	{
		if(debugOnInfo) {logger.info("Initialising for "+ejb.toString());}
		try
		{
			workflow = fApproval.fWorkflow(cWith,ejb);
			
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	public void reloadWorkflow()
	{
		transitions.clear();
		transitions.addAll(fApproval.allForParent(fbApproval.getClassTransition(), workflow.getCurrentStage()));
		
		activities.clear();
		activities.addAll(fApproval.allForParent(fbApproval.getClassActivity(), workflow));
		
		if(debugOnInfo) {logger.info("reloadWorkflow: "+transitions.size());}
	}
	
	public void prepareTransition(AT transition)
	{
		logger.info(transition.toString());
	}
}