package org.jeesl.controller.facade.system.io;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.controller.handler.system.io.fr.storage.FileRepositoryFileStorage;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.factory.json.db.tuple.t2.Json2TuplesFactory;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryStore;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;
import org.jeesl.util.comparator.pojo.BooleanComparator;
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
									TYPE extends JeeslFileType<L,D,TYPE,?>>
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
		boolean keep = BooleanComparator.active(meta.getContainer().getStorage().getKeepRemoved());
		if(!keep)
		{
			build(meta.getContainer().getStorage()).delteFileFromRepository(meta);
		}
		
		logger.info("Removing Meta "+meta.getContainer().getMetas().size()+" keep:"+keep+" "+meta.getCode());
		meta.getContainer().getMetas().remove(meta);
		logger.trace("Removing Meta "+meta.getContainer().getMetas().size());
		this.rm(meta);
	}
	
	@Override public CONTAINER moveContainer(CONTAINER container, STORAGE destination) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		container = this.find(fbFile.getClassContainer(),container);
		JeeslFileRepositoryStore<META> sourceRepo = this.build(container.getStorage());
		JeeslFileRepositoryStore<META> destinationRepo = this.build(destination);
		
		for(META meta : container.getMetas())
		{
			byte[] bytes = sourceRepo.loadFromFileRepository(meta);
			destinationRepo.saveToFileRepository(meta, bytes);
		}
		container.setStorage(destination);
		container = this.save(container);
		for(META meta : container.getMetas())
		{
			sourceRepo.delteFileFromRepository(meta);
		}
		
		return container;
	}

	@Override
	public Json2Tuples<STORAGE,TYPE> tpIoFileByStorageType()
	{
		Json2TuplesFactory<STORAGE,TYPE> jtf = new Json2TuplesFactory<STORAGE,TYPE>(this,fbFile.getClassStorage(),fbFile.getClassType());
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
		Root<META> item = cQ.from(fbFile.getClassMeta());
		
		Expression<Long> eCount = cB.count(item.<Long>get("id"));
		
		Join<META,CONTAINER> jContainer = item.join(JeeslFileMeta.Attributes.container.toString());
		Path<STORAGE> pStorage = jContainer.get(JeeslFileContainer.Attributes.storage.toString());
		Path<TYPE> pType = item.get(JeeslFileMeta.Attributes.type.toString());
		
		cQ.groupBy(pStorage.get("id"),pType.get("id"));
		cQ.multiselect(pStorage.get("id"),pType.get("id"),eCount);

//		cQ.where(cB.and(ErpPredicateBuilder.srsImplementation(cB, query, item)));
		TypedQuery<Tuple> tQ = em.createQuery(cQ);
        return jtf.buildCount(tQ.getResultList());
	}
}