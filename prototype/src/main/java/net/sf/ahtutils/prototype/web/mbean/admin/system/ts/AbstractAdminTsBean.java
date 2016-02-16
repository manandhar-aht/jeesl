package net.sf.ahtutils.prototype.web.mbean.admin.system.ts;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.system.ts.EjbTsClassFactory;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsDataFactory;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsScopeFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsBridge;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.util.comparator.ejb.ts.TsClassComparator;
import net.sf.ahtutils.util.comparator.ejb.ts.TsScopeComparator;

public class AbstractAdminTsBean <L extends UtilsLang, D extends UtilsDescription,
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
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsBean.class);
	
	protected UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> fTs;
	
	protected Class<CAT> cCategory;
	protected Class<SCOPE> cScope;
	protected Class<UNIT> cUnit;
	protected Class<TS> cTs;
	protected Class<BRIDGE> cBridge;
	protected Class<EC> cEc;
	protected Class<INT> cInt;
	protected Class<DATA> cData;
	protected Class<WS> cWs;
	
	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	
	protected EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efScope;
	protected EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efClass;
	protected EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> efData;
	
	protected Comparator<SCOPE> comparatorScope;
	protected Comparator<EC> comparatorClass;

	protected SbMultiStatusHandler<L,D,CAT> sbhCategory; public SbMultiStatusHandler<L,D,CAT> getSbhCategory() {return sbhCategory;}

	protected void initTsSuper(String[] langs, UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> fTs, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<TS> cTs, Class<BRIDGE> cBridge, Class<EC> cEc, Class<INT> cInt, Class<DATA> cData, Class<WS> cWs)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
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
		
		comparatorScope = (new TsScopeComparator<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>()).factory(TsScopeComparator.Type.position);
		comparatorClass = (new TsClassComparator<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>()).factory(TsClassComparator.Type.position);
		
		efScope = EjbTsScopeFactory.factory(cScope);
		efClass = EjbTsClassFactory.factory(cEc);
		efData = EjbTsDataFactory.factory(cData);
		
		categories = fTs.allOrderedPositionVisible(cCategory);
		sbhCategory = new SbMultiStatusHandler<L,D,CAT>(cCategory,categories); sbhCategory.selectAll();
	}
}