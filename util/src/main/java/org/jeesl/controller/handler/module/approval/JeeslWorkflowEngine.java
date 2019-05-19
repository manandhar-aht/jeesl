package org.jeesl.controller.handler.module.approval;

import org.jeesl.api.facade.module.JeeslApprovalFacade;
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
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
							RA extends JeeslRevisionAttribute<L,D,RE,?,?>
							>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowEngine.class);
	
	protected JeeslApprovalFacade<L,D,AX,AP,AS,AT,ATT,AC,MT,SR> fApproval;
	
	protected JeeslApprovalProcess<L,D,AX> process;
	
	public JeeslWorkflowEngine()
	{
		
	}
	
	
	
	
}