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
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class FileRepositoryFileStorage<STORAGE extends JeeslFileStorage<?,?,?>,
									META extends JeeslFileMeta<?,?>>
	implements JeeslFileRepositoryStore<META>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(FileRepositoryFileStorage.class);

	private final File baseDir;
	
	public FileRepositoryFileStorage(STORAGE storage)
	{
		baseDir = new File(storage.getJson());
	}
	
	@Override public META saveToFileRepository(META meta, byte[] bytes) throws UtilsConstraintViolationException, UtilsLockingException
	{
		File f = build(meta.getCode());
		logger.info(meta.getCode());
		logger.info(f.getAbsolutePath());
		try {FileUtils.writeByteArrayToFile(f, bytes);}
		catch (IOException e){throw new UtilsConstraintViolationException(e.getMessage());}
		
		return meta;
	}
	
	@Override
	public byte[] loadFromFileRepository(META meta) throws UtilsNotFoundException
	{
		File f = build(meta.getCode());
		if(!f.exists()) {throw new UtilsNotFoundException("File "+f.getAbsolutePath()+" does not exist");}
		try{return FileUtils.readFileToByteArray(f);}
		catch (IOException e) {throw new UtilsNotFoundException(e.getMessage());}
	}
	
	private File build(String uid)
	{
		uid = uid.replace("-", "");
		File l1 = new File(baseDir,uid.substring(0,2));
		File l2 = new File(l1,uid.substring(2,4));
		File l3 = new File(l2,uid.substring(4,6));
		File l4 = new File(l3,uid.substring(6,8));
		File l5 = new File(l4,uid.substring(8,10));
		return new File(l5,uid);
	}


}