package org.jeesl.api.facade.io;

import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryStore;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoFrFacade <L extends UtilsLang, D extends UtilsDescription,
								STORAGE extends JeeslFileStorage<L,D,ENGINE>,
								ENGINE extends UtilsStatus<ENGINE,L,D>,
								CONTAINER extends JeeslFileContainer<STORAGE,META>,
								META extends JeeslFileMeta<D,CONTAINER,TYPE>,
								TYPE extends JeeslFileType<L,D,TYPE,?>>
		extends UtilsFacade,JeeslFileRepositoryStore<META>
{
	CONTAINER moveContainer(CONTAINER container, STORAGE destination) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException;
	Json2Tuples<STORAGE,TYPE> tpIoFileByStorageType();
}