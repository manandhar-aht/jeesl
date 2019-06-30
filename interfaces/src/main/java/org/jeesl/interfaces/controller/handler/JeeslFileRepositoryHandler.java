package org.jeesl.interfaces.controller.handler;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public interface JeeslFileRepositoryHandler <STORAGE extends JeeslFileStorage<?,?,?>,
											CONTAINER extends JeeslFileContainer<STORAGE,?>,
											META extends JeeslFileMeta<?,CONTAINER,?>>
		extends Serializable
{
	void setDebugOnInfo(boolean debugOnInfo);
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
	
	CONTAINER getContainer();
	List<META> getMetas();
	
	//Default behavior should be transaction=false
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(W with) throws UtilsConstraintViolationException, UtilsLockingException;
	<W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE storage, W with) throws UtilsConstraintViolationException, UtilsLockingException;
	
	InputStream download(META meta) throws UtilsNotFoundException;
	
	void copyTo(JeeslFileRepositoryHandler<STORAGE,CONTAINER,META> target) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException;
	
	void deleteFile() throws UtilsConstraintViolationException, UtilsLockingException;
	
//	StreamedContent fileStream() throws Exception;
}