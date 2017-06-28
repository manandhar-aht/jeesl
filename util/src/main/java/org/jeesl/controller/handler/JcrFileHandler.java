package org.jeesl.controller.handler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoJcrFacade;
import org.jeesl.interfaces.bean.JcrBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.xml.io.File;

public class JcrFileHandler<L extends UtilsLang,D extends UtilsDescription, T extends UtilsStatus<T,L,D>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(JcrFileHandler.class);
	
	private List<File> files; public List<File> getFiles() {return files;}
	private final List<T> types; public List<T> getTypes() {return types;}
	
	private File file; public File getFile() {return file;} public void setFile(File file) {this.file = file;}
	private EjbWithId ejb; public EjbWithId getEjb() {return ejb;} public void setEjb(EjbWithId ejb) {this.ejb = ejb;}
	private boolean withTypes; public boolean isWithTypes() {return withTypes;}
	
	private final JeeslIoJcrFacade<L,D> fJcr;
	private final JcrBean bean;
	
	public JcrFileHandler(JcrBean bean, JeeslIoJcrFacade<L,D> fJcr){this(bean,fJcr,null);}
	public JcrFileHandler(JcrBean bean, JeeslIoJcrFacade<L,D> fJcr, List<T> types)
	{
		this.bean=bean;
		this.fJcr=fJcr;
		this.types=types;
		withTypes = types!=null && types.isEmpty();
		
		files = new ArrayList<File>();
	}
	
	public void reset()
	{
		files.clear();
		file=null;
		ejb=null;
	}
	
	public void reload(EjbWithId ejb)
	{
		this.ejb=ejb;
		files = fJcr.jcrFiles(ejb);
	}
	
	public void loadFile() throws UtilsNotFoundException
	{
		file = fJcr.jcrFile(ejb,file.getName());
	}
	
	public InputStream toInputStream()
	{
		return new ByteArrayInputStream(file.getData().getValue());
	}
	
	public void saveFile() throws UtilsProcessingException
	{
		boolean alreadyAvailable = false;
		for(File f : files)
		{
			if(f.getName().equals(file.getName())){alreadyAvailable=true;}
		}
		
		logger.info("Save (existing:"+alreadyAvailable+")");
		if(!alreadyAvailable)
		{
			fJcr.jcrUpload(ejb,file);
			reload(ejb);
		}
		file = null;
    }
	
	public void addFile()
	{
		file = null;
	}
	
	public void selectFile() throws UtilsNotFoundException
	{
		logger.info("selectFile");
		loadFile();
		bean.jcrFileSelected(ejb);
	}
}