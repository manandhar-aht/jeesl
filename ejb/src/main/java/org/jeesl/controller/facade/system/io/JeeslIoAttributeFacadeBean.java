package org.jeesl.controller.facade.system.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoAttributeFacadeBean<L extends UtilsLang, D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
										SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
										ITEM extends JeeslAttributeItem<CRITERIA,SET>,
										CONTAINER extends JeeslAttributeContainer<SET,DATA>,
										DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
					extends UtilsFacadeBean
					implements JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoAttributeFacadeBean.class);
	
	private final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute;
	
	public JeeslIoAttributeFacadeBean(EntityManager em, IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		super(em);
		this.fbAttribute=fbAttribute;
	}
	
	@Override
	public List<CRITERIA> fAttributeCriteria(List<CATEGORY> categories, long refId)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<CRITERIA>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<CRITERIA> cQ = cB.createQuery(fbAttribute.getClassCriteria());
		Root<CRITERIA> root = cQ.from(fbAttribute.getClassCriteria());
		
		Join<CRITERIA,CATEGORY> jCategory = root.join(JeeslAttributeCriteria.Attributes.category.toString());
		predicates.add(jCategory.in(categories));
		
		if(refId>0)
		{
			Expression<Long> eRefId = root.get(JeeslAttributeCriteria.Attributes.refId.toString());
			predicates.add(cB.equal(eRefId,refId));
		}
		
		Path<Integer> pPosition = root.get(JeeslAttributeCriteria.Attributes.position.toString());
		Path<Integer> pCategoryPosition = jCategory.get(JeeslAttributeCriteria.Attributes.position.toString());
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.asc(pCategoryPosition),cB.asc(pPosition));
		cQ.select(root);

		return em.createQuery(cQ).getResultList();
	}
	
	@Override
	public List<SET> fAttributeSets(List<CATEGORY> categories, long refId)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<SET>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SET> cQ = cB.createQuery(fbAttribute.getClassSet());
		Root<SET> root = cQ.from(fbAttribute.getClassSet());
		
		Join<SET,CATEGORY> jCategory = root.join(JeeslAttributeSet.Attributes.category.toString());
		predicates.add(jCategory.in(categories));
		
		if(refId>0)
		{
			Expression<Long> eRefId = root.get(JeeslAttributeSet.Attributes.refId.toString());
			predicates.add(cB.equal(eRefId,refId));
		}
		
		Path<Integer> pPosition = root.get(JeeslAttributeSet.Attributes.position.toString());
		Path<Integer> pCategoryPosition = jCategory.get(JeeslAttributeSet.Attributes.position.toString());
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.asc(pCategoryPosition),cB.asc(pPosition));
		cQ.select(root);

		return em.createQuery(cQ).getResultList();
	}

	@Override
	public List<DATA> fAttributeData(CONTAINER container)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbAttribute.getClassData());
		Root<DATA> data = cQ.from(fbAttribute.getClassData());
		
		Path<CONTAINER> pContainer = data.join(JeeslAttributeData.Attributes.container.toString());
		predicates.add(cB.equal(pContainer,container));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);

		TypedQuery<DATA> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override
	public DATA fAttributeData(CRITERIA criteria, CONTAINER container) throws UtilsNotFoundException
	{
		List<DATA> datas = fAttributeData(container);
		for(DATA data : datas) {if(data.getCriteria().equals(criteria)) {return data;}}
		throw new UtilsNotFoundException("no data for container");
	}
	
	@Override
	public List<DATA> fAttributeData(CRITERIA criteria, Collection<CONTAINER> containers)
	{
		List<DATA> result = new ArrayList<DATA>();
		for(CONTAINER c : containers)
		{
			try {
				result.add(fAttributeData(criteria,c));
			} catch (UtilsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public CONTAINER copy(CONTAINER container) throws UtilsConstraintViolationException, UtilsLockingException
	{
		CONTAINER c = this.save(fbAttribute.ejbContainer().build(container.getSet()));
		for(DATA data : this.fAttributeData(container))
		{
			this.save(fbAttribute.ejbData().copy(c,data));
		}
		return c;
	}
}