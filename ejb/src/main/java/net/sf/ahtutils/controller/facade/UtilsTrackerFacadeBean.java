package net.sf.ahtutils.controller.facade;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsTrackerFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsTrackerFacadeBean extends UtilsFacadeBean implements UtilsTrackerFacade
{	
	public UtilsTrackerFacadeBean(EntityManager em)
	{
		super(em);
	}
	
	@Override public <TR extends UtilsTracker<TR, TL, T, S, L, D>, TL extends UtilsTrackerLog<TR, TL, T, S, L, D>, T extends UtilsStatus<T,L,D>, S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
		List<TR> allTrackerForType(Class<TR> clTracker, Class<T> clType, T type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TR> criteriaQuery = criteriaBuilder.createQuery(clTracker);
		
		Root<TR> fromTracker = criteriaQuery.from(clTracker);
		Root<T> fromType = criteriaQuery.from(clType);
		
		Path<Object> pathType = fromTracker.get("type");
		Path<Object> pathTypeId = fromType.get("id");
		
		CriteriaQuery<TR> select = criteriaQuery.select(fromTracker);
		select.where(criteriaBuilder.equal(pathType, pathTypeId),
				criteriaBuilder.equal(pathTypeId, type.getId()));
		
		TypedQuery<TR> q = em.createQuery(select); 
		return q.getResultList();
	}

	@Override
	public <TR extends UtilsTracker<TR,TL,T,S,L,D>, TL extends UtilsTrackerLog<TR, TL, T, S, L,D>, T extends UtilsStatus<T,L,D>, S extends UtilsStatus<S,L,D>, L extends UtilsLang,D extends UtilsDescription>
		TR fTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TR> criteriaQuery = criteriaBuilder.createQuery(clTracker);
		
		Root<TR> fromTracker = criteriaQuery.from(clTracker);
		Root<T> fromType = criteriaQuery.from(clType);
		
		Path<Object> pathType   = fromTracker.get("type");
		Path<Object> pathRefId  = fromTracker.get("refId");
		Path<Object> pathTypeId = fromType.get("id");
			
		CriteriaQuery<TR> select = criteriaQuery.select(fromTracker);
		select.where(criteriaBuilder.equal(pathType, pathTypeId),
				criteriaBuilder.equal(pathTypeId, type.getId()),
				criteriaBuilder.equal(pathRefId, refId));
		
		TypedQuery<TR> q = em.createQuery(select); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+clTracker.getSimpleName()+" (type="+clType.getSimpleName()+") found for refId="+refId);}
	}
	
	@Override
	public <TR extends UtilsMailTracker<T,L,U,D>,T extends UtilsStatus<T,L,D>, L extends UtilsLang, U extends EjbWithId, D extends UtilsDescription> 
		List<TR> fMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException
	{
		CriteriaQuery<TR> select = getTrackerCQ(clTracker, clType, type, refId);
		
		TypedQuery<TR> q = em.createQuery(select);
		List<TR> result = q.getResultList();
		if(result.size()==0){throw new UtilsNotFoundException("No "+clTracker.getSimpleName()+" found for refId="+refId+" and type="+type.getCode());}
		return result;
	}

	@Override
	public <TR extends UtilsMailTracker<T, L, U,D>, T extends UtilsStatus<T,L,D>, L extends UtilsLang, U extends EjbWithId, D extends UtilsDescription>
		TR fLastMailTracker(Class<TR> clTracker, Class<T> clType, T type, long refId) throws UtilsNotFoundException
	{
		CriteriaQuery<TR> select = getTrackerCQ(clTracker, clType, type, refId);
		TypedQuery<TR> q = em.createQuery(select);
		q.setMaxResults(1);
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+clTracker.getSimpleName()+" found for refId="+refId+" and type="+type.getCode());}
	}
	
	private <TR extends UtilsMailTracker<T, L, U,D>, T extends UtilsStatus<T,L,D>, L extends UtilsLang, U extends EjbWithId,D extends UtilsDescription>
		CriteriaQuery<TR> getTrackerCQ(Class<TR> clTracker, Class<T> clType, T type, long refId)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<TR> criteriaQuery = criteriaBuilder.createQuery(clTracker);
		
		Root<TR> fromTracker = criteriaQuery.from(clTracker);
		Root<T> fromType = criteriaQuery.from(clType);
		
		Path<Object> pathType   = fromTracker.get("type");
		Path<Object> pathRefId  = fromTracker.get("refId");
		Path<Object> pathTypeId = fromType.get("id");
		Path<Date>   pRecord    = fromTracker.get("recordCreated");
			
		CriteriaQuery<TR> select = criteriaQuery.select(fromTracker);
		select.where(criteriaBuilder.equal(pathType, pathTypeId),
				criteriaBuilder.equal(pathTypeId, type.getId()),
				criteriaBuilder.equal(pathRefId, refId));
		select.orderBy(criteriaBuilder.desc(pRecord));
		return select;
	}
}
