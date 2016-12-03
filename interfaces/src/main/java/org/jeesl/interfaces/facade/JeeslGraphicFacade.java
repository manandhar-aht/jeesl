package org.jeesl.interfaces.facade;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphicFacade <L extends UtilsLang,
									D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,GS>,
									GT extends UtilsStatus<GT,L,D>,
									GS extends UtilsStatus<GS,L,D>>
			extends UtilsFacade
{	
	<T extends EjbWithId> G fGraphic(Class<T> c, long statusId) throws UtilsNotFoundException;
}