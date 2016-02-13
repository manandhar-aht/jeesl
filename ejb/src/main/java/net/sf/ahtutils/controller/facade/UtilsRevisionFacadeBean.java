package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class UtilsRevisionFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,	
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
					extends UtilsFacadeBean
					implements UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
{	
	public UtilsRevisionFacadeBean(EntityManager em)
	{
		super(em);
	}
	
	@Override public RV load(Class<RV> cView, RV view)
	{
		view = em.find(cView, view.getId());
		view.getMaps().size();
		return view;
	}
	
	@Override public RS load(Class<RS> cScope, RS scope)
	{
		scope = em.find(cScope, scope.getId());
		scope.getAttributes().size();
		return scope;
	}

	@Override public RE load(Class<RE> cEntity, RE entity)
	{
		entity = em.find(cEntity, entity.getId());
		entity.getAttributes().size();
		entity.getMaps().size();
		return entity;
	}
	
	@Override public List<RS> findScopes(Class<RS> cScope, Class<RC> cCategory, List<RC> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<RC>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cScope,ppCategory);
	}
	
	@Override public List<RE> findEntities(Class<RE> cEntity, Class<RC> cCategory, List<RC> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<RC>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cEntity,ppCategory);
	}
	
	@Override public void rm(Class<RVM> cMappingView, RVM mapping) throws UtilsConstraintViolationException
	{
		mapping = em.find(cMappingView, mapping.getId());
		mapping.getView().getMaps().remove(mapping);
		this.rmProtected(mapping);
	}

	@Override
	public RA save(Class<RE> cEntity, RE entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException
	{
		entity = this.find(cEntity, entity);
		attribute = this.saveProtected(attribute);
		if(!entity.getAttributes().contains(attribute))
		{
			entity.getAttributes().add(attribute);
			this.saveProtected(entity);
		}
		return attribute;
	}
	
	@Override public RA save(Class<RS> cScope, RS scope, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException
	{
		attribute = this.saveProtected(attribute);
		if(!scope.getAttributes().contains(attribute))
		{
			scope.getAttributes().add(attribute);
			this.saveProtected(scope);
		}
		return attribute;
	}

	@Override public void rm(Class<RE> cEntity, RE entity, RA attribute) throws UtilsConstraintViolationException, UtilsLockingException
	{
		entity = this.find(cEntity, entity);
		if(entity.getAttributes().contains(attribute))
		{
			entity.getAttributes().remove(attribute);
			this.saveProtected(entity);
		}
		this.rmProtected(attribute);		
	}

	@Override public void rm(Class<RS> cScope, RS scope, RA attribute) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(scope.getAttributes().contains(attribute))
		{
			scope.getAttributes().remove(attribute);
			this.saveProtected(scope);
		}
		this.rmProtected(attribute);		
	}
	
	@Override public <T extends EjbWithId> T jpaTree(Class<T> c, String jpa, long id) throws UtilsNotFoundException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c ");
		sb.append(" FROM "+c.getName()+" c");
		sb.append(" WHERE c.").append(jpa);
		sb.append("=:refId");
		
		TypedQuery<T> q = em.createQuery(sb.toString(), c);
		q.setParameter("refId", id);
		
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+c.getSimpleName()+" for jpa="+jpa);}
	}
}