package org.jeesl.api.facade.util;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphicFacade <L extends UtilsLang, D extends UtilsDescription,
									S extends EjbWithId,
									G extends JeeslGraphic<L,D,G,GT,FS>,
									GT extends UtilsStatus<GT,L,D>,
									FS extends UtilsStatus<FS,L,D>>
			extends UtilsFacade
{	
	G fGraphicForStatus(long statusId) throws UtilsNotFoundException;
}