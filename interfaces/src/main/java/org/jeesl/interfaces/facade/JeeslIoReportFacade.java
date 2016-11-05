package org.jeesl.interfaces.facade;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoReportFacade <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										GROUPING extends UtilsStatus<GROUPING,L,D>,
										COLAGG extends UtilsStatus<COLAGG,L,D>>
			extends UtilsFacade
{	
	
}