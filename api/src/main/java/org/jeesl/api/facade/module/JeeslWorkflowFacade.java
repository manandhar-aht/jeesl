package org.jeesl.api.facade.module;

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
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowFacade <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
										AX extends JeeslWorkflowContext<AX,L,D,?>,
										AP extends JeeslWorkflowProcess<L,D,AX>,
										AS extends JeeslWorkflowStage<L,D,AP,AST,?>,
										AST extends JeeslWorkflowStageType<AST,?,?,?>,
										ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
										APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
										WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
										WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
										ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
										AC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
										AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,L,D,?>,
										AO extends EjbWithId,
										MT extends JeeslIoTemplate<L,D,?,?,?,?>,
										MC extends JeeslTemplateChannel<L,D,MC,?>,
										SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
										AL extends JeeslApprovalLink<AW,RE>,
										AW extends JeeslApprovalWorkflow<AP,AS,WY>,
										WY extends JeeslApprovalActivity<WT,AW,FRC,USER>,
										FRC extends JeeslFileContainer<?,?>,
										USER extends JeeslUser<SR>>
			extends UtilsFacade
{	
	WT fTransitionBegin(AP process);
	
	<W extends JeeslWithWorkflow<AW>> AL fLink(AP process, W owner) throws UtilsNotFoundException;
//	<W extends JeeslWithWorkflow<AW>> AW fWorkflow(Class<W> cWith, W with) throws UtilsNotFoundException;
}