package org.jeesl.controller.handler.fr.storage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryStore;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public class FileRepositoryFileStorage<STORAGE extends JeeslFileStorage<?,?,?>,
									META extends JeeslFileMeta<?,?>>
	implements JeeslFileRepositoryStore<META>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(FileRepositoryFileStorage.class);

	private final File baseDir;
	
	public FileRepositoryFileStorage(STORAGE storage)
	{
		baseDir = new File("/tmp");
	}
	
	public META saveToFileRepository(META meta, byte[] bytes) throws UtilsConstraintViolationException, UtilsLockingException
	{
		
		File f = new File(baseDir,meta.getCode());
		try {FileUtils.writeByteArrayToFile(f, bytes);}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return meta;
	}
}