package net.sf.ahtutils.prototype.web.mbean.admin.system.ts;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.ejb.module.ts.EjbTsClassFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsScopeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsTransactionFactory;
import org.jeesl.factory.factory.TsFactoryFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.util.comparator.ejb.ts.TsClassComparator;
import net.sf.ahtutils.util.comparator.ejb.ts.TsScopeComparator;

public class AbstractAdminTsBean <L extends UtilsLang, D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
									TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>, 
									BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
									EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
									INT extends UtilsStatus<INT,L,D>,
									DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
									USER extends EjbWithId, 
									WS extends UtilsStatus<WS,L,D>,
									QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsBean.class);
	
	protected JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> fTs;
	
	protected Class<CAT> cCategory;
	protected Class<SCOPE> cScope;
	protected Class<UNIT> cUnit;
	protected Class<TS> cTs;
	private final Class<TRANSACTION> cTransaction;
	protected Class<BRIDGE> cBridge;
	protected Class<EC> cEc;
	protected Class<INT> cInt;
	protected Class<DATA> cData;
	protected Class<WS> cWs;
	
	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	
	protected EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> efScope;
	protected EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> efClass;
	protected EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> efData;
	protected EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> efTransaction;
	
	protected Comparator<SCOPE> comparatorScope;
	protected Comparator<EC> comparatorClass;

	protected SbMultiStatusHandler<L,D,CAT> sbhCategory; public SbMultiStatusHandler<L,D,CAT> getSbhCategory() {return sbhCategory;}

	public AbstractAdminTsBean(final Class<L> cL, final Class<D> cD, final Class<TRANSACTION> cTransaction)
	{
		super(cL,cD);
		this.cTransaction = cTransaction;
	}
	
	protected void initTsSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> fTs, FacesMessageBean bMessage, final Class<L> cL, final Class<D> cD, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<TS> cTs, Class<BRIDGE> cBridge, Class<EC> cEc, Class<INT> cInt, Class<DATA> cData, Class<WS> cWs)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fTs=fTs;
		this.cScope=cScope;
		this.cUnit=cUnit;
		this.cTs=cTs;
		this.cBridge=cBridge;
		this.cEc=cEc;
		this.cInt=cInt;
		this.cCategory=cCategory;
		this.cData=cData;
		this.cWs=cWs;
		
		comparatorScope = (new TsScopeComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>()).factory(TsScopeComparator.Type.position);
		comparatorClass = (new TsClassComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>()).factory(TsClassComparator.Type.position);
		
		TsFactoryFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> ffTs = TsFactoryFactory.factory(cScope,cTransaction,cEc,cData);
		efScope = ffTs.scope();
		efClass = ffTs.entityClass();
		efData = ffTs.data();
		
		categories = fTs.allOrderedPositionVisible(cCategory);
		sbhCategory = new SbMultiStatusHandler<L,D,CAT>(cCategory,categories); sbhCategory.selectAll();
	}
}