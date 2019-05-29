package org.jeesl.controller.handler.module.approval;

import java.util.List;

import org.jeesl.interfaces.controller.processor.WorkflowRecipientResolver;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.approval.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalPermissionType;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStagePermission;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStageType;
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
										AW extends JeeslApprovalWorkflow<AP,AS,AY>,
										AY extends JeeslApprovalActivity<AT,AW,USER>,
										USER extends JeeslUser<SR>
										>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowCommunicator.class);
	
	private final static boolean debugOnInfo = true;
	
	private final WorkflowRecipientResolver<SR,AW,USER> recipientResolver;
	
	public JeeslWorkflowCommunicator(WorkflowRecipientResolver<SR,AW,USER> recipientResolver)
	{
		this.recipientResolver=recipientResolver;
	}
	
	public void build(JeeslWithWorkflow<AW> workflowOwner, List<AC> communications)
	{
		for(AC communication : communications)
		{
			build(workflowOwner,communication);
		}
	}
	
	private void build(JeeslWithWorkflow<AW> workflowOwner, AC communication)
	{
		List<USER> recipients = recipientResolver.getRecipients(workflowOwner, communication.getRole());
		logger.info("Building for "+recipients.size());
	}
}