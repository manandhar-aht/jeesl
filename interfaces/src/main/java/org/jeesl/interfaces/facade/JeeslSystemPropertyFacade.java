package org.jeesl.interfaces.facade;

import org.jeesl.interfaces.model.system.util.JeeslProperty;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSystemPropertyFacade <L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											P extends JeeslProperty<L,D>>
			extends UtilsFacade
{	
	
}