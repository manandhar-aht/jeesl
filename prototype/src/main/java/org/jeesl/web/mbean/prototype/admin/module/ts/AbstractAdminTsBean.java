package org.jeesl.web.mbean.prototype.admin.module.ts;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsClassFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsScopeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsTransactionFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.util.comparator.ejb.module.ts.TsClassComparator;
import org.jeesl.util.comparator.ejb.module.ts.TsScopeComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractAdminTsBean <L extends UtilsLang, D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									TS extends JeeslTimeSeries<L,D,SCOPE,BRIDGE,INT>,
									TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
									SOURCE extends EjbWithLangDescription<L,D>, 
									BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
									EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
									INT extends UtilsStatus<INT,L,D>,
									DATA extends JeeslTsData<L,D,TS,TRANSACTION,SAMPLE,WS>,
									SAMPLE extends JeeslTsSample<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, 
									USER extends EjbWithId, 
									WS extends UtilsStatus<WS,L,D>,
									QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsBean.class);
	
	protected JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> fTs;
	
	protected final Class<CAT> cCategory;
	protected final Class<SCOPE> cScope;
	protected final Class<UNIT> cUnit;
	protected final Class<TS> cTs;
	protected final Class<TRANSACTION> cTransaction;
	protected final Class<SOURCE> cSource;
	protected final Class<BRIDGE> cBridge;
	protected final Class<EC> cEc;
	protected final Class<INT> cInt;
	protected final Class<DATA> cData;
	protected final Class<WS> cWs;
	
	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	
	protected EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efScope;
	protected EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efClass;
	protected EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efBridge;
	protected EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efData;
	protected EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efTransaction;
	
	protected Comparator<SCOPE> comparatorScope;
	protected Comparator<EC> comparatorClass;

	protected final SbMultiHandler<WS> sbhWorkspace; public SbMultiHandler<WS> getSbhWorkspace() {return sbhWorkspace;}
	protected final SbMultiHandler<CAT> sbhCategory; public SbMultiHandler<CAT> getSbhCategory() {return sbhCategory;}
	
	public AbstractAdminTsBean(final Class<L> cL, final Class<D> cD, final Class<CAT> cCategory, final Class<SCOPE> cScope, final Class<UNIT> cUnit, final Class<TS> cTs, final Class<TRANSACTION> cTransaction, final Class<SOURCE> cSource, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<INT> cInt, final Class<DATA> cData, final Class<WS> cWs)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cScope=cScope;
		this.cUnit=cUnit;
		this.cTs=cTs;
		this.cTransaction = cTransaction;
		this.cSource = cSource;
		
		this.cBridge=cBridge;
		this.cEc=cEc;
		this.cInt=cInt;

		this.cData=cData;
		this.cWs=cWs;
		
		sbhCategory = new SbMultiHandler<CAT>(cCategory,this);
		sbhWorkspace = new SbMultiHandler<WS>(cWs,this);
	}
	
	protected void initTsSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> fTs, FacesMessageBean bMessage)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fTs=fTs;
		
		comparatorScope = (new TsScopeComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>()).factory(TsScopeComparator.Type.position);
		comparatorClass = (new TsClassComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>()).factory(TsClassComparator.Type.position);
		
		TsFactoryBuilder<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> ffTs = TsFactoryBuilder.factory(cScope,cTransaction,cBridge,cEc,cData);
		efScope = ffTs.scope();
		efTransaction = ffTs.transaction();
		efClass = ffTs.entityClass();
		efData = ffTs.data();
		efBridge = ffTs.bridge();
		
		categories = fTs.allOrderedPositionVisible(cCategory);
		
		sbhCategory.fillAndSelect(fTs.allOrderedPositionVisible(cCategory));
		sbhWorkspace.fillAndSelect(fTs.allOrderedPositionVisible(cWs));
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{

	}
}