package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoFrFacade <L extends UtilsLang, D extends UtilsDescription,
								STORAGE extends JeeslFileStorage<L,D,ENGINE>,
								ENGINE extends UtilsStatus<ENGINE,L,D>
								>
		extends UtilsFacade
{
	
}