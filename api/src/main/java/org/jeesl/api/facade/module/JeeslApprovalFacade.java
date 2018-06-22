package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslApprovalFacade <L extends UtilsLang, D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									PROCESS extends JeeslApprovalProcess<L,D,CAT>>
			extends UtilsFacade
{	
	
}