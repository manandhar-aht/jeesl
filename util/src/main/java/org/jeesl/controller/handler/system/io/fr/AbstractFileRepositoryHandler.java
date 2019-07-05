package org.jeesl.controller.handler.system.io.fr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrContainerFactory;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrMetaFactory;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryHandler;
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

public abstract class AbstractFileRepositoryHandler<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									STORAGE extends JeeslFileStorage<L,D,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,TYPE>,
									TYPE extends JeeslFileType<L,D,TYPE,?>>
					implements JeeslFileRepositoryHandler<STORAGE,CONTAINER,META>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFileRepositoryHandler.class);
	
	protected boolean debugOnInfo; @Override public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}
	
	public enum Mode{directSave,deferredSave}
	public enum ContainerInit {withTransaction,withoutTransaction}
	
	private Mode mode; public void setMode(Mode mode) {this.mode = mode;}
	private ContainerInit containerInit; public void setContainerInit(ContainerInit containerInit) {this.containerInit = containerInit;}
	protected final JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr;
	protected final IoFileRepositoryFactoryBuilder<L,D,LOC,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile;
	protected final JeeslFileRepositoryCallback callback;
	
	private final JeeslFileTypeHandler<META,TYPE> fth;
	protected final EjbDescriptionFactory<D> efDescription;
	protected final EjbIoFrContainerFactory<STORAGE,CONTAINER> efContainer;
	protected final EjbIoFrMetaFactory<CONTAINER,META> efMeta;
	
	protected final List<META> metas; @Override public List<META> getMetas() {return metas;}
	protected final Map<META,File> mapDeferred; public Map<META, File> getMapDeferred() {return mapDeferred;}

	private final List<LOC> locales; 
	
	private String zipName; public String getZipName() {return zipName;} public void setZipName(String zipName) {this.zipName = zipName;}
	private String zipPrefix; public String getZipPrefix() {return zipPrefix;} public void setZipPrefix(String zipPrefix) {this.zipPrefix = zipPrefix;}

	private STORAGE storage; @Override public STORAGE getStorage() {return storage;} public void setStorage(STORAGE storage) {this.storage=storage;}
	protected CONTAINER container; @Override public CONTAINER getContainer() {return container;}
	protected META meta; public META getMeta() {return meta;} public void setMeta(META meta) {this.meta = meta;}
	private LOC locale; public LOC getLocale() {return locale;}
	
	protected File xmlFile;
	private String fileName; public String getFileName() {return fileName;} public void setFileName(String fileName) {this.fileName = fileName;}
	
	private boolean showInlineUpload; public boolean isShowInlineUpload() {return showInlineUpload;}
	
	private boolean allowUpload; public boolean isAllowUpload() {return allowUpload;}
	private boolean allowChanges; public boolean isAllowChanges() {return allowChanges;}
	private boolean allowChangeName; public boolean isAllowChangeName() {return allowChangeName;}
	private boolean allowChangeDescription; public boolean isAllowChangeDescription() {return allowChangeDescription;}
	
	public AbstractFileRepositoryHandler(JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr,
								IoFileRepositoryFactoryBuilder<L,D,LOC,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile,
								JeeslFileRepositoryCallback callback
								)
	{
		this.fFr=fFr;
		this.fbFile=fbFile;
		this.callback=callback;
		debugOnInfo = false;
		
		containerInit = ContainerInit.withoutTransaction;
		mode = Mode.directSave;
		
		fth = new JeeslFileTypeHandler<META,TYPE>(fbFile,fFr);
		efDescription = new EjbDescriptionFactory<D>(fbFile.getClassD());
		efContainer = fbFile.ejbContainer();
		efMeta = fbFile.ejbMeta();
		metas = new ArrayList<>();
		mapDeferred = new HashMap<>();
		locales = new ArrayList<>();
		zipName = "zip.zip";
		
		allowChanges = true;
		allowChangeName = false;
		allowChangeDescription = false;
		
		showInlineUpload = false;
	}
	
	public void allowControls(boolean upload, boolean name, boolean description)
	{
		this.allowUpload=upload;
		allowChangeName = name;
		allowChangeDescription = description;
		allowChanges = allowChangeName || allowChangeDescription;
	}
	
	public void setLocales(List<LOC> locales)
	{
		this.locales.clear();
		locale = null;
		this.locales.addAll(locales);
		if(locales.size()==1) {locale = locales.get(0);}
	}
	
	public <E extends Enum<E>> void initStorage(E code)
	{
		try
		{
			if(fFr==null) {throw new UtilsNotFoundException("Facade is null");}
			setStorage(fFr.fByCode(fbFile.getClassStorage(), code));
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
	}
	
	@Override public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void initSilent(W with)
	{
		try {init(with);}
		catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
	}
	@Override public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(W with) throws UtilsConstraintViolationException, UtilsLockingException
	{
		boolean reset = true;
		init(storage,with,reset);
	}
	@Override public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE initForStorage, W with) throws UtilsConstraintViolationException, UtilsLockingException
	{
		boolean reset = true;
		init(initForStorage,with,reset);
	}
	private <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void init(STORAGE initForStorage, W with, boolean reset) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(reset)
		{	// The reset is required for a special case in deferredMode
			metas.clear();
			reset(true);
		}
		if(with.getFrContainer()==null)
		{
			container = efContainer.build(initForStorage);
			if(EjbIdFactory.isSaved(with))
			{
				switch (containerInit)
				{
					case withoutTransaction:	container = fFr.save(container);break;
					case withTransaction: 		container = fFr.saveTransaction(container);break;
				}
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
			if(reset) {reload(true);}
		}
	}

	public void cancelMeta() {reset(true);}
	public void reset() {reset(true);}
	private void reset(boolean rMeta)
	{
		if(rMeta) {meta=null;}
	}
	
	public void reload() {reload(false);}
	public void reload(boolean forceFind)
	{
		if(mode.equals(Mode.directSave) || forceFind)
		{
			container = fFr.find(fbFile.getClassContainer(), container);
			metas.clear();
			metas.addAll(fFr.allForParent(fbFile.getClassMeta(),container));
		}
		else
		{
			
		}
		if(debugOnInfo) {logger.info("Reloaded "+fbFile.getClassMeta().getSimpleName()+":"+metas.size()+" for container:"+container.toString());}
	}
		
	public void addFile()
	{
		if(debugOnInfo) {logger.info("Adding File");}
		xmlFile = XmlFileFactory.build("");
		showInlineUpload = true;
	}
	
	public void addFile(java.io.File f) throws UtilsNotFoundException, FileNotFoundException, IOException {addFile(f.getName(), IOUtils.toByteArray(new FileInputStream(f)), null);}
	public void addFile(String name, byte[] bytes) throws UtilsNotFoundException {addFile(name, bytes, null);}
	public void addFile(String name, byte[] bytes, String category) throws UtilsNotFoundException
	{
		addFile();
		xmlFile.setName(name);
		xmlFile.setSize(bytes.length);
		xmlFile.setData(XmlDataFactory.build(bytes));
		meta = efMeta.build(container,name,bytes.length,new Date());
		meta.setCategory(category);
		if(mode.equals(Mode.deferredSave)) {EjbIdFactory.setNextNegativeId(meta,metas);}
		fth.updateType(meta);
	}
	
	protected void handledFileUpload()
	{
		if(debugOnInfo) {logger.info("handledFileUpload: "+meta.toString());}
		fth.updateType(meta);
		showInlineUpload = false;
	}
	
	public void selectFile()
	{
		if(debugOnInfo) {logger.info("selectFile "+meta.toString());}
		fileName = meta.getFileName();
		meta = efDescription.persistMissingLangs(fFr,locales,meta);
	}
	
	public void saveFile() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info("Saving: "+xmlFile.getName()+" Mode:"+Mode.directSave);}
		if(mode.equals(Mode.directSave))
		{
			if(debugOnInfo) {logger.info("Saving to FR "+storage.toString());}
			meta = fFr.saveToFileRepository(meta,xmlFile.getData().getValue());
			reload();
		}
		else
		{
			metas.add(meta);
			mapDeferred.put(meta,xmlFile);
		}
		
		if(debugOnInfo) {logger.info("Saved");}
		
		reset(true);
    }
	
	public <W extends JeeslWithFileRepositoryContainer<CONTAINER>> void saveDeferred(W with) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info("Saving Defrred "+metas.size());}
		this.init(storage,with,false);
		List<META> handlings = new ArrayList<>();
		handlings.addAll(metas);
		if(debugOnInfo) {logger.info("saveDeferred 2 "+metas.size()+"-"+handlings.size());}
		metas.clear();
		if(debugOnInfo) {logger.info("saveDeferred 3 "+metas.size()+"-"+handlings.size());}
		for(META m : handlings)
		{
			if(debugOnInfo) {logger.info("Save Deferred: "+m.toString());}
			if(EjbIdFactory.isUnSaved(m))
			{
				File xml = mapDeferred.get(m);
				mapDeferred.remove(m);
				m.setContainer(container);
				EjbIdFactory.negativeToZero(m);
				m = fFr.saveToFileRepository(m,xml.getData().getValue());
			}
			else
			{
				m = fFr.save(m);
			}
			metas.add(m);
		}
		reload(true);
	}
	
	public void saveMeta() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info("save meta "+meta.toString());}
		if(FilenameUtils.getExtension(fileName).equals(FilenameUtils.getExtension(meta.getFileName())))
		{
			meta.setFileName(fileName);
		}
		else {meta.setFileName(fileName+"."+FilenameUtils.getExtension(meta.getFileName()));}
		
		if(mode.equals(Mode.directSave))
		{
			meta = fFr.save(meta);
		}
		else
		{
			if(metas.contains(meta)) {metas.remove(meta);}
			metas.add(meta);
		}
		
		fileName = meta.getFileName();
		reload();
	}
	
	@Override public void deleteFile() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info("DELETING: "+meta.toString());}
		fFr.delteFileFromRepository(meta);
		reload();
		reset(true);
	}
	
	public byte[] zip() throws Exception
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zof = new ZipOutputStream(bos);
		
		for(META m : metas)
		{
			StringBuilder sb = new StringBuilder();
			if(zipPrefix!=null && zipPrefix.length()>0)
			{
				sb.append(zipPrefix).append("/");
			}
			sb.append(m.getFileName());
			
			ZipEntry ze = new ZipEntry(sb.toString());
			zof.putNextEntry(ze);
			
			zof.write(fFr.loadFromFileRepository(m));

			zof.closeEntry();
			
		}
		zof.close();
		bos.close();
		return bos.toByteArray();
	}
	
	@Override public InputStream download(META m) throws UtilsNotFoundException
	{
		if(mode.equals(Mode.directSave) || EjbIdFactory.isSaved(m))
		{
			return new ByteArrayInputStream(fFr.loadFromFileRepository(m));
		}
		else
		{
			return new ByteArrayInputStream(mapDeferred.get(m).getData().getValue());
		}
	}
	
	protected InputStream toInputStream() throws UtilsNotFoundException
	{
		return download(meta);
	}
	
	public void copyTo(JeeslFileRepositoryHandler<STORAGE,CONTAINER,META> target) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info("Copy To");
		for(META oldMeta : metas)
		{
			META newMeta = efMeta.build(target.getContainer(), oldMeta.getFileName(), oldMeta.getSize(), oldMeta.getRecord());
			newMeta.setCategory(oldMeta.getCategory());
			newMeta.setMd5Hash(oldMeta.getMd5Hash());
			newMeta.setType(oldMeta.getType());
			fFr.saveToFileRepository(newMeta, fFr.loadFromFileRepository(oldMeta));
		}
	}
	
	public void reorderMetas() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.warn("NYI until META implements position");
//		PositionListReorderer.reorder(fFr,metas);
	}
}