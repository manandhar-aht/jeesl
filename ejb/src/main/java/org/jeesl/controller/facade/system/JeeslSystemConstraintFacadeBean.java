package org.jeesl.controller.facade.system;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSystemConstraintFacadeBean<L extends UtilsLang, D extends UtilsDescription,
												SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE>,
												CATEGORY extends UtilsStatus<CATEGORY,L,D>,
												CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE>, LEVEL extends UtilsStatus<LEVEL,L,D>,
												TYPE extends UtilsStatus<TYPE,L,D>>
					extends UtilsFacadeBean
					implements JeeslSystemConstraintFacade<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE>
{	
//	private final Class<SCOPE> cScope;
	private final Class<CONSTRAINT> cConstraint;
	
	public JeeslSystemConstraintFacadeBean(EntityManager em, final Class<SCOPE> cScope, final Class<CONSTRAINT> cConstraint)
	{
		super(em);
//		this.cScope=cScope;
		this.cConstraint=cConstraint;
	}
	
	@Override public CONSTRAINT fSystemConstraint(SCOPE scope, String code) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<CONSTRAINT> cQ = cB.createQuery(cConstraint);
		Root<CONSTRAINT> constraint = cQ.from(cConstraint);
		
		Join<CONSTRAINT,SCOPE> jScope = constraint.join(JeeslConstraint.Attributes.scope.toString());
		Expression<String> eCode = constraint.get(JeeslConstraint.Attributes.code.toString());
		
		predicates.add(cB.equal(jScope,scope));
		predicates.add(cB.equal(eCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(constraint);

		TypedQuery<CONSTRAINT> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+cConstraint.getSimpleName()+" found for scope="+scope.toString()+" and code="+code);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("No unique results in "+cConstraint.getSimpleName()+" for type="+scope.toString()+" and code="+code);}
	}
}