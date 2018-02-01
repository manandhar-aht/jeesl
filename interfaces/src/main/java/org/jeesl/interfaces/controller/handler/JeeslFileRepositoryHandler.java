package org.jeesl.interfaces.controller.handler;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslFileRepositoryHandler <L extends UtilsLang, D extends UtilsDescription,
											STORAGE extends JeeslFileStorage<L,D,ENGINE>,
											ENGINE extends UtilsStatus<ENGINE,L,D>,
											CONTAINER extends JeeslFileContainer<STORAGE,META>,
											META extends JeeslFileMeta<CONTAINER,TYPE>,
											TYPE extends UtilsStatus<TYPE,L,D>>
		extends Serializable
{
	STORAGE getStorage();
	CONTAINER getContainer();
	
	//Default behaiviour should be transaction=false
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(W with, boolean withTransaction) throws UtilsConstraintViolationException, UtilsLockingException;
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE storage, W with, boolean withTransaction) throws UtilsConstraintViolationException, UtilsLockingException;
}