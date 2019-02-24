package org.jeesl.controller.facade.system.io;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.controller.handler.fr.storage.FileRepositoryFileStorage;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryStore;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.io.HashUtil;

public class JeeslIoFrFacadeBean<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									STORAGE extends JeeslFileStorage<L,D,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE>
{	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(JeeslIoFrFacadeBean.class);

	private final IoFileRepositoryFactoryBuilder<L,D,LOC,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile;
	private final Map<STORAGE,JeeslFileRepositoryStore<META>> mapStorages;
	
	public JeeslIoFrFacadeBean(EntityManager em, IoFileRepositoryFactoryBuilder<L,D,LOC,STORAGE,ENGINE,CONTAINER,META,TYPE> fbFile)
	{
		super(em);
		this.fbFile=fbFile;
		mapStorages = new HashMap<STORAGE,JeeslFileRepositoryStore<META>>();
	}
	
	private JeeslFileRepositoryStore<META> build(STORAGE storage)
	{
		if(!mapStorages.containsKey(storage))
		{
			JeeslFileRepositoryStore<META> store = new FileRepositoryFileStorage<STORAGE,META>(storage);
			mapStorages.put(storage, store);
		}
		return mapStorages.get(storage);
	}


	@Override public META saveToFileRepository(META meta, byte[] bytes) throws UtilsConstraintViolationException, UtilsLockingException
	{
		meta.setMd5Hash(HashUtil.hash(bytes));
		meta = this.saveProtected(meta);
		build(meta.getContainer().getStorage()).saveToFileRepository(meta,bytes);
		return meta;
	}
	
	@Override public byte[] loadFromFileRepository(META meta) throws UtilsNotFoundException
	{
		return build(meta.getContainer().getStorage()).loadFromFileRepository(meta);
	}

	@Override public void delteFileFromRepository(META meta) throws UtilsConstraintViolationException, UtilsLockingException
	{
		meta = this.find(fbFile.getClassMeta(),meta);
		if(!meta.getContainer().getStorage().getKeepRemoved())
		{
			build(meta.getContainer().getStorage()).delteFileFromRepository(meta);
		}
		
		logger.trace("Removing Meta "+meta.getContainer().getMetas().size());
		meta.getContainer().getMetas().remove(meta);
		logger.trace("Removing Meta "+meta.getContainer().getMetas().size());
		this.rm(meta);
	}
}