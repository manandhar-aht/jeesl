package org.jeesl.controller.handler;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class FileRepositoryHandler<L extends UtilsLang, D extends UtilsDescription,
									STORAGE extends JeeslFileStorage<L,D,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<CONTAINER,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(FileRepositoryHandler.class);
		
	private final List<META> metas; public List<META> getMetas() {return metas;}

	private META meta; public META getMeta() {return meta;} public void setMeta(META meta) {this.meta = meta;}

	public FileRepositoryHandler()
	{
		metas = new ArrayList<META>();
	}
	
	public void reset()
	{

	}
	
	public void reload(EjbWithId ejb)
	{

	}
	
	public void loadFile() throws UtilsNotFoundException
	{
//		file = fJcr.jcrFile(ejb,file.getName());
	}
	
	public InputStream toInputStream()
	{
		return null;
//		return new ByteArrayInputStream(file.getData().getValue());
	}
	
	public void saveFile() throws UtilsProcessingException
	{
/*		boolean alreadyAvailable = false;
		for(File f : files)
		{
			if(f.getName().equals(file.getName())){alreadyAvailable=true;}
		}
		
		logger.info("Save (existing:"+alreadyAvailable+")");
		if(!alreadyAvailable)
		{
			if(type!=null)
			{
				type = fJcr.find(cType,type);
				file.setCategory(type.getCode());
				logger.info("Using type:" +type.toString());
			}
			
			fJcr.jcrUpload(ejb,file);
			reload(ejb);
		}
		file = null;
*/    }
	
	public void addFile()
	{
		logger.info("Add File");
	}
	
	public void selectFile() throws UtilsNotFoundException
	{
//		logger.info("selectFile");
//		loadFile();
//		bean.jcrFileSelected(ejb);
	}
}