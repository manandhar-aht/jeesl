package org.jeesl.api.facade.module;

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

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalFacade <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
										SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
										AW extends JeeslApprovalWorkflow<AP,AS,AY>,
										AY extends JeeslApprovalActivity<AT,AW,USER>,
										USER extends JeeslUser<SR>>
			extends UtilsFacade
{	
	AT fTransitionBegin(AP process);
	<W extends JeeslWithWorkflow<AW>> AW fWorkflow(Class<W> cWith, W with) throws UtilsNotFoundException;
}