package org.jeesl.interfaces.facade;

import org.jeesl.interfaces.model.system.news.JeeslSystemNews;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSystemNewsFacade <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
										USER extends EjbWithId>
			extends UtilsFacade
{	
	
}