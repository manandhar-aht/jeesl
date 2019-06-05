package org.jeesl.controller.handler.module.workflow;

import java.util.List;

import org.jeesl.interfaces.controller.processor.WorkflowRecipientResolver;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslWorkflowCommunicator <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
										AW extends JeeslApprovalWorkflow<AP,AS,AY>,
										AY extends JeeslApprovalActivity<AT,AW,USER>,
										USER extends JeeslUser<SR>
										>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowCommunicator.class);
	
	private boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	private final WorkflowRecipientResolver<SR,AW,USER> recipientResolver;
	
	public JeeslWorkflowCommunicator(WorkflowRecipientResolver<SR,AW,USER> recipientResolver)
	{
		this.recipientResolver=recipientResolver;
		debugOnInfo = false;
	}
	
	public void build(JeeslWithWorkflow<AW> entity, List<AC> communications)
	{
		if(debugOnInfo) {logger.info("Buidling Messages for "+entity.toString());}
		for(AC communication : communications)
		{
			build(entity,communication);
		}
	}
	
	private void build(JeeslWithWorkflow<AW> entity, AC communication)
	{
		List<USER> recipients = recipientResolver.getRecipients(entity,communication.getRole());
		if(debugOnInfo) {logger.info("Building for "+recipients.size());}
	}
}