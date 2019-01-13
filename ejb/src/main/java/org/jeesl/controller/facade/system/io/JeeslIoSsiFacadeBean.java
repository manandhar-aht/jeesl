package org.jeesl.controller.facade.system.io;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.factory.json.db.tuple.t1.Json1TuplesFactory;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslIoSsiFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									SYSTEM extends JeeslIoSsiSystem,
									MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends UtilsStatus<LINK,L,D>,
									ENTITY extends JeeslRevisionEntity<?,?,?,?,?>>
					extends UtilsFacadeBean
					implements JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoSsiFacadeBean.class);
		
	private final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fbSsi;
	
	
	public JeeslIoSsiFacadeBean(EntityManager em, IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fbSsi)
	{
		super(em);
		this.fbSsi = fbSsi;
	}
	
	@Override
	public MAPPING fMapping(Class<?> ejb, Class<?> json) throws UtilsNotFoundException
	{
		ENTITY eEjb = this.fByCode(fbSsi.getClassEntity(), ejb.getName());
		ENTITY eJson = this.fByCode(fbSsi.getClassEntity(), json.getName());
		return this.oneForParents(fbSsi.getClassMapping(), JeeslIoSsiMapping.Attributes.entity.toString(), eEjb, JeeslIoSsiMapping.Attributes.json.toString(), eJson);
	}

	@Override public List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links){return fIoSsiData(mapping,links,null,null);}
	@Override public <A extends EjbWithId, B extends EjbWithId> List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links, A a, B b)
	{
		if(links!=null && links.isEmpty()) {return new ArrayList<DATA>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbSsi.getClassData());
		Root<DATA> data = cQ.from(fbSsi.getClassData());
		
		Join<DATA,MAPPING> jMapping = data.join(JeeslIoSsiData.Attributes.mapping.toString());
		predicates.add(jMapping.in(mapping));
		
		if(links!=null)
		{
			Join<DATA,LINK> jLink = data.join(JeeslIoSsiData.Attributes.link.toString());
			predicates.add(jLink.in(links));
		}
		
		if(a!=null)
		{
			Path<Long> pA = data.get(JeeslIoSsiData.Attributes.refA.toString());
			predicates.add(cB.equal(pA,a.getId()));
		}
		if(b!=null)
		{
			Path<Long> pB = data.get(JeeslIoSsiData.Attributes.refB.toString());
			predicates.add(cB.equal(pB,b.getId()));
		}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);

		TypedQuery<DATA> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}

	@Override
	public DATA fIoSsiData(MAPPING mapping, String code) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbSsi.getClassData());
		Root<DATA> data = cQ.from(fbSsi.getClassData());
		
		Join<DATA,MAPPING> jMapping = data.join(JeeslIoSsiData.Attributes.mapping.toString());
		predicates.add(jMapping.in(mapping));
		
		Expression<String> eCode = data.get(JeeslIoSsiData.Attributes.code.toString());
		predicates.add(cB.equal(eCode,code));

		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);

		TypedQuery<DATA> tQ = em.createQuery(cQ);
		
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+fbSsi.getClassData().getSimpleName()+" for "+mapping.toString()+" for code="+code);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Results for "+fbSsi.getClassData().getSimpleName()+" and code="+code+" not unique");}
	}
	
	@Override
	public <T extends EjbWithId> DATA fIoSsiData(MAPPING mapping, T ejb) throws UtilsNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbSsi.getClassData());
		Root<DATA> data = cQ.from(fbSsi.getClassData());
		
		Join<DATA,MAPPING> jMapping = data.join(JeeslIoSsiData.Attributes.mapping.toString());
		predicates.add(jMapping.in(mapping));
		
		Expression<Long> eId = data.get(JeeslIoSsiData.Attributes.localId.toString());
		predicates.add(cB.equal(eId,ejb.getId()));

		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);

		TypedQuery<DATA> tQ = em.createQuery(cQ);
		
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+fbSsi.getClassData().getSimpleName()+" for "+mapping.toString()+" for id="+ejb.getId());}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Results for "+fbSsi.getClassData().getSimpleName()+" and id="+ejb.getId()+" not unique");}
	}
	
	@Override public Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping){return tpIoSsiLinkForMapping(mapping,null,null);}
	@Override public <A extends EjbWithId, B extends EjbWithId> Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping, A a, B b)
	{
		Json1TuplesFactory<LINK> jtf = new Json1TuplesFactory<LINK>(this,fbSsi.getClassLink());
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
		Root<DATA> data = cQ.from(fbSsi.getClassData());
		
		Expression<Long> cCount = cB.count(data);
		
		Path<LINK> pLink = data.get(JeeslIoSsiData.Attributes.link.toString());
		Join<DATA,MAPPING> jMapping = data.join(JeeslIoSsiData.Attributes.mapping.toString());
		predicates.add(jMapping.in(mapping));
		
		if(a!=null)
		{
			Path<Long> pA = data.get(JeeslIoSsiData.Attributes.refA.toString());
			predicates.add(cB.equal(pA,a.getId()));
		}
		if(b!=null)
		{
			Path<Long> pB = data.get(JeeslIoSsiData.Attributes.refB.toString());
			predicates.add(cB.equal(pB,b.getId()));
		}
		
		cQ.groupBy(pLink.get("id"));
		cQ.multiselect(pLink.get("id"),cCount);
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
	       
		TypedQuery<Tuple> tQ = em.createQuery(cQ);
        return jtf.buildCount(tQ.getResultList());
	}
}