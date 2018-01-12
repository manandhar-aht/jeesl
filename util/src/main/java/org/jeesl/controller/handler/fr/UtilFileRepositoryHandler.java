package org.jeesl.controller.handler.fr;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrContainerFactory;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrMetaFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.factory.xml.io.XmlDataFactory;
import net.sf.exlp.factory.xml.io.XmlFileFactory;
import net.sf.exlp.xml.io.File;

public class UtilFileRepositoryHandler<L extends UtilsLang, D extends UtilsDescription,
									STORAGE extends JeeslFileStorage<L,D,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<CONTAINER,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UtilFileRepositoryHandler.class);

	private final JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr;
	private final IoFileRepositoryFactoryBuilder<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile;
	private final JeeslFileRepositoryCallback callback;
	
	private final EjbIoFrContainerFactory<STORAGE,CONTAINER> efContainer;
	private final EjbIoFrMetaFactory<CONTAINER,META> efMeta;
	
	private final List<META> metas; public List<META> getMetas() {return metas;}

	private CONTAINER container; public CONTAINER getContainer() {return container;}
	private META meta; public META getMeta() {return meta;} public void setMeta(META meta) {this.meta = meta;}
	private File xmlFile;
	
	public UtilFileRepositoryHandler(JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr,
								IoFileRepositoryFactoryBuilder<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile,
								JeeslFileRepositoryCallback callback)
	{
		this.fFr=fFr;
		this.fbFile=fbFile;
		this.callback=callback;
		
		efContainer = fbFile.ejbContainer();
		efMeta = fbFile.ejbMeta();
		metas = new ArrayList<META>();
	}
	
	public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE storage, W with) throws UtilsConstraintViolationException, UtilsLockingException
	{
		metas.clear();
		reset(true);
		if(with.getFrContainer()==null)
		{
			container = efContainer.build(storage);
			if(EjbIdFactory.isSaved(with))
			{
				container = fFr.save(container);
				if(callback!=null)
				{
					callback.fileRepositoryContainerSaved(with);
				}
			}
		}
		else
		{
			container = with.getFrContainer();
			reload();
		}
	}
	
	private void reset(boolean rMeta)
	{
		if(rMeta) {meta=null;}
	}
	
	public void reload()
	{
		metas.clear();
		metas.addAll(fFr.allForParent(fbFile.getClassMeta(),container));
		logger.info("Reloaded "+fbFile.getClassMeta().getSimpleName()+" "+metas.size());
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
	
	public void addFile()
	{
		logger.info("Adding File");
		xmlFile = XmlFileFactory.build("");
	}
	
	public void addFile(String name, byte[] bytes) throws UtilsNotFoundException
	{
		addFile();
		xmlFile.setName(name);
		xmlFile.setSize(bytes.length);
		xmlFile.setData(XmlDataFactory.build(bytes));
		meta = efMeta.build(container,name,bytes.length,new Date());
		meta.setType(fFr.fByCode(fbFile.getClassType(), JeeslFileType.Code.unknown));
	}
	
	public void handleFileUpload(FileUploadEvent event) throws UtilsNotFoundException
	{
		logger.info("Handling FileUpload: "+event.getFile().getFileName());
		xmlFile.setName(event.getFile().getFileName());
		xmlFile.setSize(event.getFile().getSize());
		xmlFile.setData(XmlDataFactory.build(event.getFile().getContents()));
		meta = efMeta.build(container,event.getFile().getFileName(),event.getFile().getSize(),new Date());
		meta.setType(fFr.fByCode(fbFile.getClassType(), JeeslFileType.Code.unknown));
		
		logger.info(meta.toString());
    }
	
	public void saveFile() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Saving: "+xmlFile.getName());
		meta = fFr.saveToFileRepository(meta,xmlFile.getData().getValue());
		reload();
		reset(true);
    }
	
	public void selectFile() throws UtilsNotFoundException
	{
//		logger.info("selectFile");
//		loadFile();
//		bean.jcrFileSelected(ejb);
	}
}