package org.jeesl.api.facade.system.graphic;

import java.util.List;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphicFacade <L extends UtilsLang, D extends UtilsDescription,
									S extends EjbWithId,
									G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
			extends UtilsFacade
{	
	G fGraphicForStatus(long statusId) throws UtilsNotFoundException;
	<W extends EjbWithGraphic<G>> G fGraphic(Class<W> c, W w) throws UtilsNotFoundException;
	<W extends EjbWithGraphic<G>> G fGraphic(Class<W> c, long id) throws UtilsNotFoundException; 
	<T extends EjbWithGraphic<G>> List<T> allWithGraphicFigures(Class<T> c);
}