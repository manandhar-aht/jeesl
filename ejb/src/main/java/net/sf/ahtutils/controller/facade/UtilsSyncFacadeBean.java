package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.sync.EjbSyncFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.sync.UtilsSync;

import org.jeesl.api.facade.core.JeeslSyncFacade;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class UtilsSyncFacadeBean <L extends UtilsLang,
									D extends UtilsDescription,
									STATUS extends UtilsStatus<STATUS,L,D>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									SYNC extends UtilsSync<L,D,STATUS,CATEGORY>>  
	extends UtilsFacadeBean implements JeeslSyncFacade<L,D,STATUS,CATEGORY,SYNC>
{	
	public UtilsSyncFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public SYNC fSync(Class<SYNC> cSync, CATEGORY category, String code) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<SYNC> cQ = cB.createQuery(cSync);
        Root<SYNC> root = cQ.from(cSync);
        
        Path<CATEGORY> pathCategory = root.get("category");
        Path<String> pathCode = root.get("code");
        
        Predicate pCategory = cB.equal(pathCategory,category);
        Predicate pCode = cB.equal(pathCode,code);
        
        cQ.where(cB.and(pCategory,pCode));
        cQ.select(root);
        
		TypedQuery<SYNC> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found category:"+category.getCode()+" for code="+code);}
	}

	@Override
	public SYNC fcSync(Class<SYNC> cSync, Class<STATUS> cStatus, CATEGORY category, String code)
	{
		SYNC sync = null;
		
		try
		{
			sync = fSync(cSync,category,code);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				STATUS status = this.fByCode(cStatus,UtilsSync.Code.never.toString());
				
				EjbSyncFactory<L,D,STATUS,CATEGORY,SYNC> ef = EjbSyncFactory.factory(cSync);
				sync = ef.build(category,status,code);
				em.persist(sync);
			}
			catch (UtilsNotFoundException e1) {e1.printStackTrace();}
		}
		return sync;
	}

	@Override
	public boolean checkValid(Class<SYNC> cSync, SYNC sync, long seconds)
	{
		sync = em.find(cSync,sync.getId());
		
		DateTime dtSync = new DateTime(sync.getRecord());
		DateTime dtNow = new DateTime(sync.getRecord());
		
		boolean diffOk = Seconds.secondsBetween(dtSync,dtNow).getSeconds()<seconds;
		boolean statusOk = sync.getStatus().getCode().equals(UtilsSync.Code.success.toString());
		return (diffOk && statusOk);
	}

	
}
