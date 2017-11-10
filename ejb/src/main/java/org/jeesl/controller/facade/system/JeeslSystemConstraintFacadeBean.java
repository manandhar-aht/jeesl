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
import org.jeesl.factory.builder.system.ConstraintFactoryBuilder;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSystemConstraintFacadeBean<L extends UtilsLang, D extends UtilsDescription,
												ALGCAT extends UtilsStatus<ALGCAT,L,D>,
												ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
												SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
												CONCAT extends UtilsStatus<CONCAT,L,D>,
												CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
												LEVEL extends UtilsStatus<LEVEL,L,D>,
												TYPE extends UtilsStatus<TYPE,L,D>,
												RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
					extends UtilsFacadeBean
					implements JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>
{	
	private final ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint;

	
	public JeeslSystemConstraintFacadeBean(EntityManager em, ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint, final Class<SCOPE> cScope, final Class<CONSTRAINT> cConstraint)
	{
		super(em);
		this.fbConstraint=fbConstraint;
	}
	
	@Override public CONSTRAINT fSystemConstraint(SCOPE scope, String code) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<CONSTRAINT> cQ = cB.createQuery(fbConstraint.getClassConstraint());
		Root<CONSTRAINT> constraint = cQ.from(fbConstraint.getClassConstraint());
		
		Join<CONSTRAINT,SCOPE> jScope = constraint.join(JeeslConstraint.Attributes.scope.toString());
		Expression<String> eCode = constraint.get(JeeslConstraint.Attributes.code.toString());
		
		predicates.add(cB.equal(jScope,scope));
		predicates.add(cB.equal(eCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(constraint);

		TypedQuery<CONSTRAINT> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+fbConstraint.getClassConstraint().getSimpleName()+" found for scope="+scope.toString()+" and code="+code);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("No unique results in "+fbConstraint.getClassConstraint().getSimpleName()+" for type="+scope.toString()+" and code="+code);}
	}
}