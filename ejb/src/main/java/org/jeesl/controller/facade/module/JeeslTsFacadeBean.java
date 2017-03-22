package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsBridgeFactory;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslTsFacadeBean<L extends UtilsLang,
							D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							WS extends UtilsStatus<WS,L,D>,
							QAF extends UtilsStatus<QAF,L,D>>
					extends UtilsFacadeBean
					implements JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>
{	
	
	private final Class<DATA> cData;
	
	private EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efTs;
	private EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efBridge;
	
	public JeeslTsFacadeBean(EntityManager em, final Class<DATA> cData)
	{
		super(em);
		this.cData=cData;
	}

	@Override public List<SCOPE> findScopes(Class<SCOPE> cScope, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<CAT>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cScope,ppCategory);
	}
	
	@Override public List<EC> findClasses(Class<EC> cClass, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<CAT>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cClass,ppCategory);
	}
	
	@Override public BRIDGE fcBridge(Class<BRIDGE> cBridge, EC entityClass, long refId) throws UtilsConstraintViolationException
	{
		try {return fBridge(cBridge, entityClass, refId);}
		catch (UtilsNotFoundException ex)
		{
			if(efBridge==null){efBridge = new EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>(cBridge);}
			BRIDGE bridge = efBridge.build(entityClass, refId);
			return this.persist(bridge);
		}
	}
	@Override public BRIDGE fBridge(Class<BRIDGE> cBridge, EC entityClass, long refId) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<BRIDGE> cQ = cB.createQuery(cBridge);
		Root<BRIDGE> from = cQ.from(cBridge);
		
		Path<EC> pClass = from.get("entityClass");
		Path<Long> pRef = from.get("refId");
		
		CriteriaQuery<BRIDGE> select = cQ.select(from);
		select.where(cB.equal(pClass, entityClass),cB.equal(pRef, refId));
		try	{return em.createQuery(select).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+cBridge.getName()+" found for entityClass/refId");}
	}

	@Override public TS fcTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsConstraintViolationException
	{
		try {return fTimeSeries(cTs, scope, interval, bridge);}
		catch (UtilsNotFoundException e)
		{
			if(efTs==null){efTs = new EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>(cTs);}
			TS ts = efTs.build(scope, interval, bridge);
			return this.persist(ts);
		}
	}
	@Override public TS fTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TS> cQ = cB.createQuery(cTs);
		Root<TS> from = cQ.from(cTs);
		
		Path<SCOPE> pScope = from.get("scope");
		Path<INT> pInterval = from.get("interval");
		Path<BRIDGE> pBridge = from.get("bridge");
		
		CriteriaQuery<TS> select = cQ.select(from);
		select.where(cB.equal(pScope, scope),cB.equal(pInterval, interval),cB.equal(pBridge, bridge));
		
		try	{return em.createQuery(select).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+cTs.getName()+" found for scope/interval/bridge");}
	}
	
	@Override public List<DATA> fData(WS workspace, TS timeSeries){return fData(workspace,timeSeries,null,null);}
	@Override
	public List<DATA> fData(WS workspace, TS timeSeries, Date from, Date to)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(cData);
		Root<DATA> data = cQ.from(cData);
		
		predicates.add(cB.equal(data.<WS>get(JeeslTsData.Attributes.workspace.toString()), workspace));
		predicates.add(cB.equal(data.<TS>get(JeeslTsData.Attributes.timeSeries.toString()), timeSeries));
		
		Expression<Date> eRecord = data.get(JeeslTsData.Attributes.record.toString());
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(eRecord, from));}
		if(to!=null){predicates.add(cB.lessThan(eRecord,to));}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		
		return em.createQuery(cQ).getResultList();
	}
}