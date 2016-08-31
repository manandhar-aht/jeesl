package net.sf.ahtutils.controller.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.behaviour.EjbEquals;
import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbMergeable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.date.EjbWithTimeline;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidFrom;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidFromUntil;
import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.graphic.UtilsWithGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.util.UtilsProperty;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithType;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithTypeCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionType;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionTypeVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public class UtilsFacadeBean implements UtilsFacade 
{
	final static Logger logger = LoggerFactory.getLogger(UtilsFacadeBean.class);
	
	private static boolean isLoggingEnabled = false; 
	
	protected EntityManager em;
	private boolean handleTransaction;
	
	public UtilsFacadeBean(EntityManager em){this(em,false);}
	public UtilsFacadeBean(EntityManager em, boolean handleTransaction)
	{
		this.em=em;
		this.handleTransaction=handleTransaction;
	}
	
	@SuppressWarnings("unchecked")
	@Override public <L extends UtilsLang,D extends UtilsDescription, S extends EjbWithId, G extends UtilsGraphic<L,D,G,GT,GS>, GT extends UtilsStatus<GT,L,D>, GS extends UtilsStatus<GS,L,D>> S load(Class<S> cS, S status)
	{
		status = em.find(cS, status.getId());
		if(UtilsWithGraphic.class.isAssignableFrom(cS))
		{
			if(((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic()!=null){((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().getId();}
		}
		
		return status;
	}
	
	//Persist
	@Override public <T extends EjbSaveable> T saveTransaction(T o) throws UtilsConstraintViolationException, UtilsLockingException{return saveProtected(o);}
	@Override public <T extends EjbSaveable> T save(T o) throws UtilsConstraintViolationException,UtilsLockingException {return saveProtected(o);}
	
	@Override public <T extends EjbSaveable> void save(List<T> list) throws UtilsConstraintViolationException,UtilsLockingException {for(T t : list){saveProtected(t);}}
	@Override public <T extends EjbSaveable> void saveTransaction(List<T> list) throws UtilsConstraintViolationException,UtilsLockingException {for(T t : list){saveProtected(t);}}
	
	@Override
	public <E extends EjbEquals<T>, T extends EjbWithId> boolean equalsAttributes(Class<T> c, E object)
	{
		if(object.getId()==0){return false;}
		else {return object.equalsAttributes(em.find(c,object.getId()));}
	}
	
	@Override public <T extends EjbMergeable> T mergeTransaction(T o) throws UtilsConstraintViolationException, UtilsLockingException {return update(o);}
	@Override public <T extends EjbMergeable> T merge(T o) throws UtilsConstraintViolationException, UtilsLockingException {return this.update(o);}
	
	

	public <T extends EjbWithId> T saveProtected(T o) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(o.getId()==0){return this.persist(o);}
		else{return this.update(o);}
	}
	
	public <T extends Object> T persist(T o) throws UtilsConstraintViolationException
	{
		try
		{
			if(handleTransaction){em.getTransaction().begin();}
			em.persist(o);
			if(handleTransaction){em.getTransaction().commit();}
		}
		catch (Exception e)
		{
			if(handleTransaction){em.getTransaction().rollback();}
			if(e instanceof javax.validation.ConstraintViolationException)
			{
				throw new UtilsConstraintViolationException(e.getMessage());
			}
			if(e instanceof javax.persistence.PersistenceException)
			{
				if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
				{
					throw new UtilsConstraintViolationException(e.getCause().getMessage());
				}
				if(e.getCause() instanceof org.hibernate.PersistentObjectException)
				{
					throw new UtilsConstraintViolationException(e.getCause().getMessage());
				}
				else
				{
					StringBuffer sb = new StringBuffer();
					sb.append("Not handled error:").append(javax.persistence.PersistenceException.class.getName());
					sb.append(" with cause:").append(e.getCause().getClass().getName());
					logger.error(sb.toString());
					e.printStackTrace();
				}
			}
			else
			{
				logger.error("It's not a "+javax.persistence.PersistenceException.class.getName()+" ...");
				System.err.println("This Error is not handled: "+e.getClass().getName());
				e.printStackTrace();
			}
//			System.err.println("Error by "+ex.getCausedByException().getClass().getCanonicalName());
//			if(ex.getCausedByException().getClass().getSimpleName().equals(ConstraintViolationException.class.getSimpleName())){throw (ConstraintViolationException)ex.getCausedByException();}
//			else if(ex.getCausedByException().getClass().getSimpleName().equals(EntityExistsException.class.getSimpleName()))
			{
	//			logger.warn(ex.getCausedByException());
			}
//			else {throw ex;}
		}
	    return o;
	}
	
	public <T extends Object> T update(T o) throws UtilsConstraintViolationException, UtilsLockingException
	{
		try
		{
			if(handleTransaction){em.getTransaction().begin();}
			em.merge(o);
			em.flush();
			if(handleTransaction){em.getTransaction().commit();}
		}
		catch (Exception e)
		{
			if(handleTransaction){em.getTransaction().rollback();}
//			System.out.println("Exception in update");
//			e.printStackTrace();
			
//			System.err.println(javax.validation.ConstraintViolationException.class.getSimpleName()+" "+(e instanceof javax.validation.ConstraintViolationException));
//			System.err.println(javax.persistence.PersistenceException.class.getSimpleName()+" "+(e instanceof javax.persistence.PersistenceException));
//			System.err.println(javax.persistence.OptimisticLockException.class.getSimpleName()+" "+(e instanceof javax.persistence.OptimisticLockException));
			
			if(e instanceof javax.validation.ConstraintViolationException)
			{
				throw new UtilsConstraintViolationException(e.getMessage());
			}
			if(e instanceof javax.persistence.OptimisticLockException)
			{
				throw new UtilsLockingException(e.getMessage());
			}
			if(e instanceof javax.persistence.PersistenceException)
			{
				if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
				{
					throw new UtilsConstraintViolationException(e.getCause().getMessage());
				}
				else
				{
					System.err.println("This Error is not handled: "+e.getClass().getName());
					e.printStackTrace();
				}
			}
			
			System.err.println("(end) This Error is not handled: "+e.getClass().getName());
			e.printStackTrace();
		}
		return o;
	}
	
	public <T extends EjbWithId> T find(Class<T> type, T t)
	{
		T o = em.find(type,t.getId());
		return o;
	}
	public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException
	{
		T o = em.find(type,id);
		if(o==null){throw new UtilsNotFoundException("No entity "+type+" with id="+id);}
		return o;
	}
	
	@Override public <T extends EjbWithId> List<T> find(Class<T> cl, Set<Long> ids)
	{
		return find(cl,new ArrayList<Long>(ids));
	}
	
	@Override public <T extends EjbWithId> List<T> find(Class<T> cl, List<Long> ids)
	{
		if(ids==null || ids.size()==0){return new ArrayList<T>();}
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<T> cQ = cB.createQuery(cl);
        Root<T> root = cQ.from(cl);
        Path<Long> path = root.get("id");
        cQ.where(cB.isTrue(path.in(ids)));

		TypedQuery<T> q = em.createQuery(cQ); 
		return q.getResultList();
	}
	
	@Override public <T extends EjbWithCode, E extends Enum<E>> T fByCode(Class<T> type, E code) throws UtilsNotFoundException {return this.fByCode(type, code.toString());}
	@Override public <T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<T> cQ = cB.createQuery(type);
        Root<T> root = cQ.from(type);
        cQ = cQ.where(root.<T>get("code").in(code));

		TypedQuery<T> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+type.getSimpleName()+" for code="+code);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Results for "+type.getSimpleName()+" and code="+code+" not unique");}
	}
	
	@Override public <T extends EjbWithTypeCode> T fByTypeCode(Class<T> c, String type, String code) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<T> cQ = cB.createQuery(c);
        Root<T> root = cQ.from(c);
        cQ = cQ.where(cB.and(root.<T>get("code").in(code),root.<T>get("type").in(type)));

		TypedQuery<T> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+c.getSimpleName()+" for code="+code+" type="+type);}
	}
	
	@Override public <T extends EjbWithNonUniqueCode> List<T> allByCode(Class<T> type, String code)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery = criteriaQuery.where(root.<T>get("code").in(code));

		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		return q.getResultList();
	}
	
	@Override public <T extends EjbWithEmail> T fByEmail(Class<T> clazz, String email) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery = criteriaQuery.where(root.<T>get("email").in(email));

		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+clazz.getSimpleName()+" for email="+email);}
	}
	
	@Override
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Integer(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}
	@Override
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Long(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public <T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Boolean(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type, String key, Date defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Date(new Long(t.getValue()));
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}
	@Override
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return t.getValue();
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}
	private <T extends UtilsProperty> T valueForKey(Class<T> type, String key) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery = criteriaQuery.where(root.<T>get("key").in(key));

		T result;
		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		try	{result= q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+type.getSimpleName()+" for key="+key);}
		return result;
	}
	
	@Override public <T extends Object> List<T> all(Class<T> type){return all(type,0);}
	@Override public <T extends Object> List<T> all(Class<T> type, int maxResults)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> from = criteriaQuery.from(type);
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		
		TypedQuery<T> typedQuery = em.createQuery(select);
		if(maxResults>0){typedQuery.setMaxResults(maxResults);}
		
		return typedQuery.getResultList();
	}
	
	@Override public <T, I extends EjbWithId> List<T> allOrderedParent(Class<T> cl,String by, boolean ascending, String p1Name, I p1)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(cl);
		Root<T> from = cQ.from(cl);
		
		Path<Object> p1Path = from.get(p1Name);
		Path<Object> pOrder = from.get(by);
		
		CriteriaQuery<T> select = cQ.select(from);
		if(ascending){select.orderBy(cB.asc(pOrder));}
		else{select.orderBy(cB.desc(pOrder));}
		select.where(cB.equal(p1Path, p1.getId()));
		
		return em.createQuery(select).getResultList();
	}
		
	@Override public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, boolean ascending, String p1Name, I p1,Date fromRecord, Date toRecord)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(cl);
		Root<T> from = cQ.from(cl);
		
		Path<Object> p1Path = from.get(p1Name);
		Expression<Date> eRecord = from.get("record");
		
		CriteriaQuery<T> select = cQ.select(from);
		if(ascending){select.orderBy(cB.asc(eRecord));}
		else{select.orderBy(cB.desc(eRecord));}
		select.where(cB.equal(p1Path, p1.getId()),
				cB.lessThanOrEqualTo(eRecord, toRecord),
				cB.greaterThanOrEqualTo(eRecord, fromRecord));
		
		return em.createQuery(select).getResultList();
	}
	
	@Override
	public <T extends EjbWithRecord, P extends EjbWithId> List<T> allOrderedParentsRecordBetween(Class<T> c, boolean ascending, String parentName, List<P> parents, Date from, Date to)
	{
		if(parents==null || parents.size()==0){return new ArrayList<T>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(c);
		Root<T> root = cQ.from(c);
		
		Path<Object> pParent = root.get(parentName);
		Expression<Date> eRecord = root.get("record");
		
		predicates.add(pParent.in(parents));
		predicates.add(cB.greaterThanOrEqualTo(eRecord, from));
		predicates.add(cB.lessThanOrEqualTo(eRecord, to));

		CriteriaQuery<T> select = cQ.select(root);
		if(ascending){select.orderBy(cB.asc(eRecord));}else{select.orderBy(cB.desc(eRecord));}
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return em.createQuery(select).getResultList();
	}
	
	@Override public <T extends EjbWithPositionType, E extends Enum<E>> List<T> allOrderedPosition(Class<T> cT, E enu)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cb.createQuery(cT);
		Root<T> from = criteriaQuery.from(cT);
		Path<Object> pathType = from.get("type");
		
		Expression<Integer> eOrder = from.get("position");
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		select.orderBy(cb.asc(eOrder));
		select.where(cb.equal(pathType, enu.toString()));
		
		return em.createQuery(select).getResultList();
	}
	
	@Override public <T extends EjbWithPositionTypeVisible, E extends Enum<E>> List<T> allOrderedPositionVisible(Class<T> cT, E enu)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cb.createQuery(cT);
		Root<T> from = criteriaQuery.from(cT);
		
		Path<Boolean> pathVisible = from.get("visible");
		Path<String> pathType = from.get("type");
		Expression<Integer> eOrder = from.get("position");
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		select.orderBy(cb.asc(eOrder));
		select.where(cb.and(cb.equal(pathType, enu.toString()),cb.equal(pathVisible, true)));
		
		return em.createQuery(select).getResultList();
	}
		
	@Override
	public <T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(Class<T> cl)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(cl);
		Root<T> from = criteriaQuery.from(cl);
		Path<Object> pathVisible = from.get("visible");
		
		Expression<Date> eOrder = from.get("position");
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		select.orderBy(cB.asc(eOrder));
		select.where(cB.equal(pathVisible, true));
		
		return em.createQuery(select).getResultList();
	}
	
	@Override public <T extends EjbWithPositionVisibleParent, P extends EjbWithId> List<T> allOrderedPositionVisibleParent(Class<T> cl, P parent)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(cl);
		Root<T> from = cQ.from(cl);
		
		CriteriaQuery<T> select = cQ.select(from);
		
		Path<Object> pathVisible = from.get("visible");
		Expression<Date> eOrder = from.get("position");
		select.orderBy(cB.asc(eOrder));
		
		try
		{
			T prototype = cl.newInstance();
			Path<Object> p1Path = from.get(prototype.resolveParentAttribute());
			select.where(cB.and(cB.equal(p1Path, parent.getId()),cB.equal(pathVisible, true)));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return em.createQuery(select).getResultList();
	}
	
	@Override public <T extends EjbWithPositionParent, P extends EjbWithId> List<T> allOrderedPositionParent(Class<T> cl, P parent)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(cl);
		Root<T> from = cQ.from(cl);
		
		CriteriaQuery<T> select = cQ.select(from);
		
		Expression<Date> eOrder = from.get("position");
		select.orderBy(cB.asc(eOrder));
		
		try
		{
			T prototype = cl.newInstance();
			Path<Object> p1Path = from.get(prototype.resolveParentAttribute());
			select.where(cB.equal(p1Path, parent.getId()));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return em.createQuery(select).getResultList();
	}
	
	@Override public <T extends EjbRemoveable> void rmTransaction(T o) throws UtilsConstraintViolationException {rmProtected(o);}
	@Override public <T extends EjbRemoveable> void rm(T o) throws UtilsConstraintViolationException {rmProtected(o);}
	@Override public <T extends EjbRemoveable> void rm(Set<T> set) throws UtilsConstraintViolationException{rm(new ArrayList<T>(set));}
	@Override public <T extends EjbRemoveable> void rm(List<T> list) throws UtilsConstraintViolationException{for(T t : list){rm(t);}}
	
	public <T extends Object> void rmProtected(T o) throws UtilsConstraintViolationException
	{
		if(isLoggingEnabled){logger.info("Removing "+o.toString());}
		try
		{
			if(isLoggingEnabled){logger.info("Before merge");}
			o=em.merge(o);
			if(isLoggingEnabled){logger.info("Before remove");}
			em.remove(o);
			if(isLoggingEnabled){logger.info("Before flush");}
			em.flush();
			if(isLoggingEnabled){logger.info("After flush");}
		}
		catch(javax.persistence.PersistenceException e)
		{
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
			{
				throw new UtilsConstraintViolationException("Delete Referential Integrity check failed for "+o.getClass().getSimpleName()+". Object may be used somewhere else: "+o);
			}
			throw(e);
		}
	}
	
	public <T extends EjbWithType> List<T> allForType(Class<T> clazz, String type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery = criteriaQuery.where(root.<T>get("type").in(type));
        
		TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	public <T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery = criteriaQuery.where(root.<T>get("name").in(name));

		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for name="+name);}
	}
	
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, long nr) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
	    
	    Root<T> from = criteriaQuery.from(type);
	    Path<Object> pathParent = from.get(parentName);
	    Path<Object> pathNr = from.get("nr");
	    
	    CriteriaQuery<T> select = criteriaQuery.select(from);
	    select.where( criteriaBuilder.equal(pathParent, parent.getId()),
	    		      criteriaBuilder.equal(pathNr, nr));

		TypedQuery<T> q = em.createQuery(select); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for nr="+nr);}
	}
	
	public <T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> pathParent = fromType.get(parentName);

	    Expression<Date> fromDate = fromType.get("validFrom");
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.equal(pathParent, id),
	    		      cB.lessThanOrEqualTo(fromDate, validFrom));
	    select.orderBy(cB.desc(fromDate));
	    
		TypedQuery<T> q = em.createQuery(select);
		q.setMaxResults(1);
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for "+parentName+".id="+id+" validFrom="+validFrom);}
	}
	@Override public <T extends EjbWithParentAttributeResolver, I extends EjbWithId> T oneForParent(Class<T> type, I p1) throws UtilsNotFoundException
	{
		T prototype = null;
		try {prototype = type.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		List<T> list = allForParent(type,prototype.resolveParentAttribute(), p1);
		if(list.size()>1){throw new UtilsNotFoundException("More than one result found for "+type.getSimpleName()+" and "+prototype.resolveParentAttribute()+"=="+p1);}
		if(list.size()==0){throw new UtilsNotFoundException("No "+type.getSimpleName()+" found for "+prototype.resolveParentAttribute()+"=="+p1);}
		return list.get(0);
	}
	@Override public <T extends EjbWithId, I extends EjbWithId> T oneForParent(Class<T> cl, String p1Name, I p1) throws UtilsNotFoundException
	{
		List<T> list = allForParent(cl, p1Name, p1);
		if(list.size()>1){throw new UtilsNotFoundException("More than one result found for "+cl.getSimpleName()+" and "+p1Name+"=="+p1);}
		if(list.size()==0){throw new UtilsNotFoundException("No "+cl.getSimpleName()+" found for "+p1Name+"=="+p1);}
		return list.get(0);
	}
	@Override public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2) throws UtilsNotFoundException
	{
		List<T> list = allForParent(cl, p1Name, p1, p2Name, p2);
		if(list.size()>1){throw new UtilsNotFoundException("More than one "+cl.getSimpleName()+" found for "+p1Name+"={"+p1+"} and "+p2Name+"={"+p2+"}");}
		if(list.size()==0){throw new UtilsNotFoundException("No "+cl.getSimpleName()+" found for "+p1Name+"={"+p1+"} and "+p2Name+"={"+p2+"}");}
		return list.get(0);
	}
	@Override public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2, String p3Name, I p3) throws UtilsNotFoundException
	{
		List<T> list = allForParent(cl,p1Name,p1,p2Name,p2,p3Name,p3);
		if(list.size()>1){throw new UtilsNotFoundException("More than one result found for "+cl.getSimpleName()+" and "+p1Name+"=="+p1+" and "+p2Name+"=="+p2+" and "+p3Name+"=="+p3);}
		if(list.size()==0){throw new UtilsNotFoundException("No "+cl.getSimpleName()+" found for "+cl.getSimpleName()+" and "+p1Name+"=="+p1+" and "+p2Name+"=="+p2+" and "+p3Name+"=="+p3);}
		return list.get(0);
	}
	
	@Override public <T extends EjbWithId, P extends EjbWithId> T oneForParents(Class<T> cl, List<ParentPredicate<P>> parents) throws UtilsNotFoundException
	{
		List<T> list = this.fForAndOrParents(cl, parents, ParentPredicate.empty());
		if(list.size()>1){throw new UtilsNotFoundException("More than one result found for Query");}
		if(list.size()==0){throw new UtilsNotFoundException("No "+cl.getSimpleName()+" found for Query");}
		return list.get(0);
	}
	
	@Override public <T extends EjbWithParentAttributeResolver, I extends EjbWithId> List<T> allForParent(Class<T> type, I p1)
	{
		T prototype = null;
		try {prototype = type.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return allForParent(type,prototype.resolveParentAttribute(), p1);
	}
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1){return allForParent(type,p1Name, p1,0);}
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1,int maxResults)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> p1Path = fromType.get(p1Name);
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.equal(p1Path, p1.getId()));
	    
		TypedQuery<T> q = em.createQuery(select);
		if(maxResults>0){q.setMaxResults(maxResults);}
		
		try	{return q.getResultList();}
		catch (NoResultException ex){return new ArrayList<T>();}
	}
	
	//******************** ORDERED *****************
	
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> cl) {return allOrdered(cl, "position", true);}
	@Override public <T extends EjbWithCode> List<T> allOrderedCode(Class<T> cl) {return allOrdered(cl, "code", true);}
	
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> cl, boolean ascending)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> select = cqOrdered(cb, cl, "record", ascending);
		return em.createQuery(select).getResultList();
	}
	@Override public <T extends EjbWithValidFrom> List<T> allOrderedValidFrom(Class<T> cl, boolean ascending)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> select = cqOrdered(cb, cl, "validFrom", ascending);
		return em.createQuery(select).getResultList();
	}
	
	@Override public <T extends Object> List<T> allOrdered(Class<T> cl, String by, boolean ascending)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> select = cqOrdered(cb, cl, by, ascending);
		return em.createQuery(select).getResultList();
	}
	
	private <T extends Object> CriteriaQuery<T> cqOrdered(CriteriaBuilder cb, Class<T> cl, String by, boolean ascending)
	{
		CriteriaQuery<T> criteriaQuery = cb.createQuery(cl);
		Root<T> from = criteriaQuery.from(cl);
		
		Expression<Date> eOrder = from.get(by);
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(ascending){select.orderBy(cb.asc(eOrder));}
		else{select.orderBy(cb.desc(eOrder));}
		
		return select;
	}
	
	
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr,boolean ascending)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** allOrderedForParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("ascending:" +ascending);
			logger.trace("AND "+lpAnd.size());
			logger.trace("OR "+lpOr.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);
		Expression<Date> eRecord = from.get("record");
		
		Predicate pOr = cB.or(ParentPredicate.array(cB, from, lpOr));
		Predicate pAnd = cB.and(ParentPredicate.array(cB, from, lpAnd));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(ascending){select.orderBy(cB.asc(eRecord));}
		else{select.orderBy(cB.desc(eRecord));}
		if(lpOr==null || lpOr.size()==0)
		{
			select.where(pAnd);
		}
		else
		{
			select.where(cB.and(pAnd,pOr));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	// ************************************
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> p1Path = fromType.get(p1Name);
	    Path<Object> p2Path = fromType.get(p2Name);
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.and(cB.equal(p1Path, p1.getId()),
	    					 cB.equal(p2Path, p2.getId())));
	    
		TypedQuery<T> q = em.createQuery(select);
		
		try	{return q.getResultList();}
		catch (NoResultException ex){return new ArrayList<T>();}
	}
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2, String p3Name, I p3)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> p1Path = fromType.get(p1Name);
	    Path<Object> p2Path = fromType.get(p2Name);
	    Path<Object> p3Path = fromType.get(p3Name);
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.and(cB.equal(p1Path, p1.getId()),
	    					 cB.equal(p2Path, p2.getId()),
	    					 cB.equal(p3Path, p3.getId())));
	    
		TypedQuery<T> q = em.createQuery(select);
		
		try	{return q.getResultList();}
		catch (NoResultException ex){return new ArrayList<T>();}
	}
	
	@Override
	public <T extends EjbWithId, P extends EjbWithId> List<T> allForOrParents(Class<T> queryClass, List<ParentPredicate<P>> parents)
	{
		List<ParentPredicate<P>> lAnd = ParentPredicate.empty();
		return fForAndOrParents(queryClass,  lAnd,parents);
	}
	
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fForAndOrParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("AND "+lpAnd.size());
			logger.trace("OR "+lpOr.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);

		Predicate pOr = cB.or(ParentPredicate.array(cB, from, lpOr));
		Predicate pAnd = cB.and(ParentPredicate.array(cB, from, lpAnd));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(lpOr==null || lpOr.size()==0)
		{
			if(lpAnd==null || lpAnd.size()==0)
			{
				return new ArrayList<T>();
			}
			else
			{
				select.where(pAnd);
			}
		}
		else
		{
			select.where(cB.and(pAnd,pOr));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	@Override
	public <T extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> allForOrOrParents(Class<T> cl, List<ParentPredicate<OR1>> or1, List<ParentPredicate<OR2>> or2)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fForAndOrParents");
			logger.trace("QueryClass:" +cl.getName());
			logger.trace("OR1 "+or1.size());
			logger.trace("OR2 "+or2.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(cl);
		 
		Root<T> from = criteriaQuery.from(cl);

		Predicate pOr1 = cB.or(ParentPredicate.array(cB, from, or1));
		Predicate pOr2 = cB.or(ParentPredicate.array(cB, from, or2));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		select.where(cB.and(pOr1,pOr2));
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	@Override public <T extends EjbWithId, P extends EjbWithId, GP extends EjbWithId> List<T> allForGrandParent(Class<T> queryClass, Class<P> pClass, String pName, GP gpObject, String gpName)
	{
		ParentPredicate<GP> ppGrandparent = ParentPredicate.create(gpObject, gpName);
		return fForAndOrGrandParents(queryClass,pClass,pName,ParentPredicate.list(ppGrandparent),ParentPredicate.empty());
	}
	
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fForAndOrPGrandParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("AND "+lpAnd.size());
			logger.trace("OR "+lpOr.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);
		Root<P> fromParent = criteriaQuery.from(parentClass);
		
		Path<Object> pathToParent = from.get(parentName);
	    Path<Object> pathParentId = fromParent.get("id");
		
		Predicate pOr = cB.or(ParentPredicate.array(cB, fromParent, lpOr));
		Predicate pAnd = cB.and(ParentPredicate.array(cB, fromParent, lpAnd));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(lpOr==null || lpOr.size()==0)
		{
			select.where(pAnd,cB.equal(pathToParent, pathParentId));
		}
		else
		{
			select.where(cB.and(pAnd,pOr),cB.equal(pathToParent, pathParentId));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fGrandParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("OR1 "+lpOr1.size());
			logger.trace("OR2 "+lpOr2.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);
		Root<P> fromParent = criteriaQuery.from(parentClass);
		
		Path<Object> pathToParent = from.get(parentName);
	    Path<Object> pathParentId = fromParent.get("id");
		
		Predicate pOr1 = cB.or(ParentPredicate.array(cB, fromParent, lpOr1));
		Predicate pOr2 = cB.or(ParentPredicate.array(cB, fromParent, lpOr2));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(lpOr1==null || lpOr2==null || lpOr1.size()==0 || lpOr2.size()==0)
		{
			logger.trace("Returning empty List");
			return new ArrayList<T>();
		}
		else
		{
			logger.trace("Executing");
			select.where(pOr1,pOr2,cB.equal(pathToParent, pathParentId));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	public <T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord, Date from, Date to)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(clRecord);
		Root<T> root = cQ.from(clRecord);
		
		Expression<Date> dRecord = root.get("record");
		
		Predicate startAfterFrom = cB.greaterThanOrEqualTo(dRecord, from);
		Predicate startBeforeTo = cB.lessThan(dRecord, to);
		
		Predicate predicate = cB.and(startAfterFrom,startBeforeTo);
		
		CriteriaQuery<T> select = cQ.select(root);
		select.where(predicate);
		
		select.orderBy(cB.asc(dRecord));
		
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	@Override public <T extends EjbWithRecord> T fFirst(Class<T> clRecord){return fSingle(clRecord,true);}
	@Override public <T extends EjbWithRecord> T fLast(Class<T> clRecord){return fSingle(clRecord,false);}
	private <T extends EjbWithRecord> T fSingle(Class<T> clRecord, boolean first)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(clRecord);
		Root<T> root = cQ.from(clRecord);
		
		Expression<Date> dRecord = root.get("record");
		
		CriteriaQuery<T> select = cQ.select(root);
		if(first){select.orderBy(cB.asc(dRecord));}
		else{select.orderBy(cB.desc(dRecord));}
		
		TypedQuery<T> q = em.createQuery(select);
		q.setMaxResults(1);
		return q.getSingleResult();
	}

	@Override
	public <T extends EjbWithTimeline> List<T> between(Class<T> clTimeline, Date from, Date to)
	{
		return between(clTimeline, from, to, ParentPredicate.empty(), ParentPredicate.empty());
	}
	
	@Override
	public <T extends EjbWithValidFromUntil> T oneInRange(Class<T> c,Date record) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(c);
		Root<T> root = cQ.from(c);
		
		Expression<Date> dStart = root.get("validFrom");
		Expression<Date> dEnd   = root.get("validUntil");
		
		Predicate pLower = cB.lessThanOrEqualTo(dStart, record);
	    Predicate pUpper = cB.greaterThan(dEnd, record);
				    
		CriteriaQuery<T> select = cQ.select(root);
		select.where(cB.and(pLower,pUpper));
		
		TypedQuery<T> q = em.createQuery(select);
		List<T> result = q.getResultList();
		if(result.size()==1){return result.get(0);}
		else if(result.size()==0){throw new UtilsNotFoundException("No result for "+record.toString());}
		else {throw new UtilsNotFoundException("Mutliple results");}
	}
	
	@Override
	public <T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T> between(Class<T> clTimeline,Date from, Date to, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> cQ = cB.createQuery(clTimeline);
		Root<T> root = cQ.from(clTimeline);
		
		Expression<Date> dStart = root.get("startDate");
		Expression<Date> dEnd   = root.get("endDate");
		
		//After
	    Predicate startAfterFrom = cB.greaterThanOrEqualTo(dStart, from);
	    Predicate endAfterFrom = cB.greaterThanOrEqualTo(dEnd, from);
	    Predicate endAfterTo = cB.greaterThanOrEqualTo(dEnd, to);
	    
	    //Before
	    Predicate startBeforeTo = cB.lessThan(dStart, to);
	    Predicate startBeforeFrom = cB.lessThan(dStart, from);
	    Predicate endBeforeTo = cB.lessThan(dEnd, to);
		
		Predicate pOnlyStartAndStartInRange = cB.and(cB.isNull(dEnd),startAfterFrom,startBeforeTo);
		Predicate pStartAndEndInRange = cB.and(cB.isNotNull(dEnd),startAfterFrom,endBeforeTo);
		Predicate pStartOutsideEndInRange = cB.and(cB.isNotNull(dEnd),startBeforeFrom,endAfterFrom,endBeforeTo);
		Predicate pStartInRangeEndOutside = cB.and(cB.isNotNull(dEnd),startAfterFrom,startBeforeTo,endAfterTo);
		Predicate pStartBeforeRangeEndAfterRange = cB.and(cB.isNotNull(dEnd),startBeforeFrom,endAfterTo);
			
		Predicate pTime = cB.or(pOnlyStartAndStartInRange,pStartAndEndInRange,pStartOutsideEndInRange,pStartInRangeEndOutside,pStartBeforeRangeEndAfterRange);
		
		Predicate pAnd = cB.and(ParentPredicate.array(cB, root, lpAnd));
		Predicate pOr = cB.or(ParentPredicate.array(cB, root, lpOr));
				    
		CriteriaQuery<T> select = cQ.select(root);
		
		boolean noAnd = (lpAnd==null || lpAnd.size()==0);
		boolean noOr = (lpOr==null || lpOr.size()==0);
		
		if(noOr && noAnd) {select.where(pTime);}
		else if(noOr && !noAnd) {select.where(pTime,pAnd);}
		else if(!noOr && noAnd) {select.where(pTime,pOr);}
		else {select.where(pTime,pOr,pAnd);}
		
		select.orderBy(cB.asc(dStart));
		
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	//Year
	@Override
	public <T extends EjbWithYear, P extends EjbWithId> T fByYear(Class<T> type, String parentName, P p, int year) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> pathParent = fromType.get(parentName);

	    Expression<Integer> fromYear = fromType.get("year");
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.equal(pathParent, p.getId()),
	    		      cB.equal(fromYear, year));
	    
	    TypedQuery<T> q = em.createQuery(select);
		q.setMaxResults(1);
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for "+parentName+".id="+p.getId()+" year="+year);}
	}
}