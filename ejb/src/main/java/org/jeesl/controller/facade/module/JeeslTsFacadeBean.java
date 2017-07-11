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
import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslTsFacadeBean<L extends UtilsLang,
							D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
							TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
							SOURCE extends EjbWithLangDescription<L,D>, 
							BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
							EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
							SAMPLE extends JeeslTsSample<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, 
							USER extends EjbWithId, 
							WS extends UtilsStatus<WS,L,D>,
							QAF extends UtilsStatus<QAF,L,D>>
					extends UtilsFacadeBean
					implements JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>
{
	private final Class<TRANSACTION> cTransaction;
	private final Class<DATA> cData;
	
	private EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efTs;
	private EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efBridge;
	
	public JeeslTsFacadeBean(EntityManager em, final Class<TRANSACTION> cTransaction, final Class<DATA> cData)
	{
		super(em);
		this.cTransaction=cTransaction;
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
	
	@Override public <T extends EjbWithId> BRIDGE fcBridge(Class<BRIDGE> cBridge, EC entityClass, T ejb) throws UtilsConstraintViolationException
	{
		try {return fBridge(cBridge, entityClass, ejb);}
		catch (UtilsNotFoundException ex)
		{
			if(efBridge==null){efBridge = new EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cBridge);}
			BRIDGE bridge = efBridge.build(entityClass, ejb.getId());
			return this.persist(bridge);
		}
	}
	@Override public <T extends EjbWithId> BRIDGE fBridge(Class<BRIDGE> cBridge, EC entityClass, T ejb) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<BRIDGE> cQ = cB.createQuery(cBridge);
		Root<BRIDGE> from = cQ.from(cBridge);
		
		Path<EC> pClass = from.get("entityClass");
		Path<Long> pRef = from.get("refId");
		
		CriteriaQuery<BRIDGE> select = cQ.select(from);
		select.where(cB.equal(pClass, entityClass),cB.equal(pRef, ejb.getId()));
		try	{return em.createQuery(select).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+cBridge.getName()+" found for entityClass/refId");}
	}

	@Override public TS fcTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsConstraintViolationException
	{
		try {return fTimeSeries(cTs, scope, interval, bridge);}
		catch (UtilsNotFoundException e)
		{
			if(efTs==null){efTs = new EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cTs);}
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
	
	@Override
	public List<DATA> fData(TRANSACTION transaction)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(cData);
		Root<DATA> data = cQ.from(cData);
		
		predicates.add(cB.equal(data.<TRANSACTION>get(JeeslTsData.Attributes.transaction.toString()), transaction));
		Expression<Date> eRecord = data.get(JeeslTsData.Attributes.record.toString());
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
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
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
	}
	
	@Override
	public void deleteTransaction(TRANSACTION transaction) throws UtilsConstraintViolationException
	{
		transaction = em.find(cTransaction, transaction.getId());
		this.rmProtected(transaction);
	}

	@Override
	public List<TRANSACTION> fTransactions(List<USER> users, Date from, Date to)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TRANSACTION> cQ = cB.createQuery(cTransaction);
		Root<TRANSACTION> data = cQ.from(cTransaction);
		
		Expression<Date> eRecord = data.get(JeeslTsTransaction.Attributes.record.toString());
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(eRecord, from));}
		if(to!=null){predicates.add(cB.lessThan(eRecord,to));}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
	}
}