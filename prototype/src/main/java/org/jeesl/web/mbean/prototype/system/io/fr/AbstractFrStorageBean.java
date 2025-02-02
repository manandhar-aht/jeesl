package org.jeesl.web.mbean.prototype.system.io.fr;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.controller.handler.system.io.fr.JeeslFileTypeHandler;
import org.jeesl.controller.handler.tuple.JsonTuple2Handler;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrStorageFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractFrStorageBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem,
									STORAGE extends JeeslFileStorage<L,D,SYSTEM,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,TYPE,STATUS>,
									TYPE extends JeeslFileType<L,D,TYPE,?>,
									STATUS extends JeeslFileStatus<L,D,STATUS,?>>
						extends AbstractAdminBean<L,D>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFrStorageBean.class);
	
	private JeeslIoFrFacade<L,D,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr;
	private final IoFileRepositoryFactoryBuilder<L,D,LOC,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS> fbFr;
	
	private final JsonTuple2Handler<STORAGE,TYPE> thCount; public JsonTuple2Handler<STORAGE, TYPE> getThCount() {return thCount;}
	private final EjbIoFrStorageFactory<SYSTEM,STORAGE> efStorage;
	private JeeslFileTypeHandler<META,TYPE> fth;
	
	private List<STORAGE> storages; public List<STORAGE> getStorages() {return storages;}
	private List<ENGINE> engines; public List<ENGINE> getEngines() {return engines;}
	
	private STORAGE storage; public STORAGE getStorage() {return storage;} public void setStorage(STORAGE storage) {this.storage = storage;}
	private TYPE typeUnknown;public TYPE getTypeUnknown() {return typeUnknown;}

	protected AbstractFrStorageBean(IoFileRepositoryFactoryBuilder<L,D,LOC,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS> fbFr, JeeslComparatorProvider<TYPE> jcpB)
	{
		super(fbFr.getClassL(),fbFr.getClassD());
		this.fbFr=fbFr;
		efStorage = fbFr.ejbStorage();
		thCount = new JsonTuple2Handler<STORAGE,TYPE>(fbFr.getClassStorage(),fbFr.getClassType());
		thCount.setComparatorProviderB(jcpB);
	}
	
	protected void initStorage(JeeslIoFrFacade<L,D,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr,
									JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fFr=fFr;
		fth = new JeeslFileTypeHandler<>(fbFr,fFr);
		try
		{
			typeUnknown = fFr.fByCode(fbFr.getClassType(), JeeslFileType.Code.unknown);
		}
		catch (UtilsNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reloadStorages();
		engines = fFr.allOrderedPositionVisible(fbFr.getClassEngine());
		thCount.init(fFr.tpIoFileByStorageType());
		
	}
	
	public void resetStorage() {reset(true);}
	private void reset(boolean rStorage)
	{
		if(rStorage) {storage=null;}
	}
	
	private void reloadStorages()
	{
		storages = fFr.all(fbFr.getClassStorage());
	}
	
	public void addStorage()
	{
		reset(true);
		storage = efStorage.build();
		storage.setName(efLang.createEmpty(localeCodes));
		storage.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveStorage() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(storage));}
		storage.setEngine(fFr.find(fbFr.getClassEngine(),storage.getEngine()));
		storage = fFr.save(storage);
		reloadStorages();
	}
	
	public void deleteStorage() throws UtilsConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(storage));}
		fFr.rm(storage);
		reloadStorages();
		reset(true);
	}
	
	public void selectStorage()
	{
		storage = fFr.find(fbFr.getClassStorage(), storage);
		storage = efLang.persistMissingLangs(fFr, localeCodes, storage);
		storage = efDescription.persistMissingLangs(fFr, localeCodes, storage);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(storage));}
		reset(false);
	}
	
	public void fixType()
	{	
		if(typeUnknown!=null && fth!=null)
		{
			int i=0;
			for(META meta : fFr.allForType(fbFr.getClassMeta(), typeUnknown))
			{
				String code = meta.getType().getCode();
				fth.updateType(meta);
				if(!code.contentEquals(meta.getType().getCode()))
				{
					i++;
					try {fFr.save(meta);}
					catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
				}
				if(i==250)
				{
					logger.info("Breaking loop to prevent timeout");
					break;
				}
			}
		}
		thCount.init(fFr.tpIoFileByStorageType());
	}
	
	public void reorderStorages() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fFr, storages);}
}