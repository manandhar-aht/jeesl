package org.jeesl.api.facade.module;

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
										AL extends JeeslApprovalLink<AW,RE>,
										AW extends JeeslApprovalWorkflow<AP,AS,AY>,
										AY extends JeeslApprovalActivity<AT,AW,USER>,
										USER extends JeeslUser<SR>>
			extends UtilsFacade
{	
	AT fTransitionBegin(AP process);
	
	<W extends JeeslWithWorkflow<AW>> AL fLink(AP process, W owner) throws UtilsNotFoundException;
//	<W extends JeeslWithWorkflow<AW>> AW fWorkflow(Class<W> cWith, W with) throws UtilsNotFoundException;
}