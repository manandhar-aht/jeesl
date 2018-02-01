package org.jeesl.controller.handler.fr;

import java.io.InputStream;
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

public abstract class AbstractFileRepositoryHandler<L extends UtilsLang, D extends UtilsDescription,
									STORAGE extends JeeslFileStorage<L,D,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<CONTAINER,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
	implements org.jeesl.interfaces.controller.handler.JeeslFileRepositoryHandler<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFileRepositoryHandler.class);
	
	private boolean debugOnInfo = true;

	protected final JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr;
	protected final IoFileRepositoryFactoryBuilder<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile;
	protected final JeeslFileRepositoryCallback callback;
	
	protected final EjbIoFrContainerFactory<STORAGE,CONTAINER> efContainer;
	protected final EjbIoFrMetaFactory<CONTAINER,META> efMeta;
	
	protected final List<META> metas; public List<META> getMetas() {return metas;}

	private STORAGE storage; @Override public STORAGE getStorage() {return storage;}
	protected CONTAINER container; @Override public CONTAINER getContainer() {return container;}
	protected META meta; public META getMeta() {return meta;} public void setMeta(META meta) {this.meta = meta;}
	protected File xmlFile;
	
	public AbstractFileRepositoryHandler(JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr,
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
	
	public <E extends Enum<E>> void initStorage(E code)
	{
		try
		{
			storage = fFr.fByCode(fbFile.getClassStorage(), code);
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	@Override public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(W with, boolean withTransaction) throws UtilsConstraintViolationException, UtilsLockingException {init(storage,with,withTransaction);}
	@Override public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE initForStorage, W with, boolean withTransaction) throws UtilsConstraintViolationException, UtilsLockingException
	{
		metas.clear();
		reset(true);
		if(with.getFrContainer()==null)
		{
			container = efContainer.build(initForStorage);
			if(EjbIdFactory.isSaved(with))
			{
				if(withTransaction){container = fFr.saveTransaction(container);}
				else {container = fFr.save(container);}
				if(debugOnInfo) {logger.info("Saved container "+container.toString());}
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
	
	public void reset()
	{
		
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
	
	public void saveFile() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Saving: "+xmlFile.getName());
		meta = fFr.saveToFileRepository(meta,xmlFile.getData().getValue());
		reload();
		reset(true);
    }
}