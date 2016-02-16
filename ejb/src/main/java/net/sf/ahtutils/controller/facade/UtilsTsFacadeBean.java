package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsBridgeFactory;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsFactory;
import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsBridge;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;

public class UtilsTsFacadeBean<L extends UtilsLang,
							D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							BRIDGE extends UtilsTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							WS extends UtilsStatus<WS,L,D>,
							QAF extends UtilsStatus<QAF,L,D>>
					extends UtilsFacadeBean
					implements UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>
{	
	private EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efTs;
	private EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efBridge;
	
	public UtilsTsFacadeBean(EntityManager em)
	{
		super(em);
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
	
	@Override public List<DATA> fData(Class<DATA> cData, WS workspace, TS timeSeries)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(cData);
		Root<DATA> from = cQ.from(cData);
		
		Path<WS> pWs = from.get("workspace");
		Path<TS> pTs = from.get("timeSeries");
		
		CriteriaQuery<DATA> select = cQ.select(from);
		select.where(cB.equal(pWs, workspace),cB.equal(pTs, timeSeries));
		
		return em.createQuery(select).getResultList();
	}
}