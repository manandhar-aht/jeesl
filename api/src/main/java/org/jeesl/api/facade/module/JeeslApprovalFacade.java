package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslApprovalFacade <L extends UtilsLang, D extends UtilsDescription,
									CTX extends JeeslApprovalContext<CTX,L,D,?>,
									AP extends JeeslApprovalProcess<L,D,CTX>,
									AS extends JeeslApprovalStage<L,D,AP>,
									T extends JeeslApprovalTransition<L,D,AS>,
									C extends JeeslApprovalCommunication<T,MT,MR>,
									MT extends JeeslIoTemplate<L,D,?,?,?,?>,
									MR extends JeeslSecurityRole<?,?,?,?,?,?,?>>
			extends UtilsFacade
{	
	
}