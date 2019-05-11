package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslApprovalFacade <L extends UtilsLang, D extends UtilsDescription,
									CTX extends JeeslApprovalContext<CTX,L,D,?>,
									PROCESS extends JeeslApprovalProcess<L,D,CTX>>
			extends UtilsFacade
{	
	
}