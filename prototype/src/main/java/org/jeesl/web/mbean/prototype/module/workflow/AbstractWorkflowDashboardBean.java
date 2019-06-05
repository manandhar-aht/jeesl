package org.jeesl.web.mbean.prototype.module.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.controller.handler.module.workflow.JeeslWorkflowEngine;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.io.IoTemplateFactoryBuilder;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractWorkflowDashboardBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
											AX extends JeeslWorkflowContext<AX,L,D,?>,
											AP extends JeeslWorkflowProcess<L,D,AX>,
											AS extends JeeslWorkflowStage<L,D,AP,AST>,
											AST extends JeeslWorkflowStageType<AST,?,?,?>,
											ASP extends JeeslWorkflowStagePermission<AS,APT,SR>,
											APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
											AT extends JeeslWorkflowTransition<L,D,AS,ATT>,
											ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
											AC extends JeeslWorkflowCommunication<AT,MT,SR>,
											AA extends JeeslWorkflowAction<AT,AB,AO,RE,RA>,
											AB extends JeeslWorkflowBot<AB,L,D,?>,
											AO extends EjbWithId,
											MT extends JeeslIoTemplate<L,D,?,?,?,?>,
											SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
											RE extends JeeslRevisionEntity<L,D,?,?,RA>,
											RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
											AL extends JeeslApprovalLink<AW,RE>,
											AW extends JeeslApprovalWorkflow<AP,AS,AY>,
											AY extends JeeslApprovalActivity<AT,AW,USER>,
											USER extends JeeslUser<SR>>
				extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractWorkflowDashboardBean.class);

	private JeeslApprovalFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fWorkflow;
	private JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision;
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval;
	private final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision;
	private final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?> fbSecurity;
	
	JeeslWorkflowEngine<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> wfEngine;
	
	private final SbSingleHandler<AX> sbhContext; public SbSingleHandler<AX> getSbhContext() {return sbhContext;}
	private final SbSingleHandler<AP> sbhProcess; public SbSingleHandler<AP> getSbhProcess() {return sbhProcess;}
	
	private final List<AW> workflows; public List<AW> getWorkflows() {return workflows;}
	
	protected AW workflow; public AW getWorkflow() {return workflow;} public void setProcess(AW workflow) {this.workflow = workflow;}

	public AbstractWorkflowDashboardBean(final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval,
											final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision,
											final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?> fbSecurity,
											final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbApproval=fbApproval;
		this.fbRevision=fbRevision;
		this.fbSecurity=fbSecurity;
		this.fbTemplate=fbTemplate;
		
		sbhContext = new SbSingleHandler<AX>(fbApproval.getClassContext(),this);
		sbhProcess = new SbSingleHandler<AP>(fbApproval.getClassProcess(),this);
		
		workflows = new ArrayList<>();
	}
	
	protected void postConstructProcess(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
										JeeslApprovalFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fWorkflow,
										JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision,
										JeeslWorkflowEngine<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> wfEngine)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fWorkflow=fWorkflow;
		this.fRevision=fRevision;
		this.wfEngine=wfEngine;
		
		initPageSettings();
		
		reloadProcesses();
		if(sbhProcess.isSelected())
		{
			reloadWorkflows();
		}
	}
	
	
	protected void initPageSettings()
	{

		sbhContext.setList(fWorkflow.allOrderedPositionVisible(fbApproval.getClassContext()));
		sbhContext.setDefault();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbApproval.getClassContext(), sbhContext.getList()));}
	}
	
	private void reset()
	{
		
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(item instanceof JeeslWorkflowContext) {reloadProcesses();}
		else if(item instanceof JeeslWorkflowProcess)
		{
			reloadWorkflows();
		}
	}
	
	public void reloadProcesses()
	{
		sbhProcess.update(fWorkflow.all(fbApproval.getClassProcess()),sbhProcess.getSelection());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassProcess(), sbhProcess.getList(),sbhContext.getSelection()));}
	}
	
	public void reloadWorkflows()
	{
		workflows.clear();
		workflows.addAll(fWorkflow.all(fbApproval.getClassWorkflow()));
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassWorkflow(), workflows));}	
	}
	
}