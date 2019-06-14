package org.jeesl.factory.builder.module;

import java.util.Comparator;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsClassFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataPointFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsMutliPointFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsScopeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsTransactionFactory;
import org.jeesl.factory.mc.ts.McTimeSeriesFactory;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.util.comparator.ejb.module.ts.TsScopeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
								ST extends UtilsStatus<ST,L,D>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
								TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
								TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER,?>,
								SOURCE extends EjbWithLangDescription<L,D>, 
								BRIDGE extends JeeslTsBridge<EC>,
								EC extends JeeslTsEntityClass<L,D,CAT>,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
								POINT extends JeeslTsDataPoint<DATA,MP>,
								SAMPLE extends JeeslTsSample,
								USER extends EjbWithId, 
								WS extends UtilsStatus<WS,L,D>,
								QAF extends UtilsStatus<QAF,L,D>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(TsFactoryBuilder.class);
	
	private final Class<CAT> cCategory; public Class<CAT> getClassCategory() {return cCategory;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<ST> cScopeType; public Class<ST> getClassScopeType() {return cScopeType;}
	private final Class<UNIT> cUnit; public Class<UNIT> getClassUnit()  {return cUnit;}
	private final Class<MP> cMp; public Class<MP> getClassMp() {return cMp;}
	private final Class<TS> cTs; public Class<TS> getClassTs() {return cTs;}
	private final Class<TRANSACTION> cTransaction; public Class<TRANSACTION> getClassTransaction() {return cTransaction;}
	private final Class<SOURCE> cSource; public Class<SOURCE> getClassSource() {return cSource;}
	private final Class<BRIDGE> cBridge; public Class<BRIDGE> getClassBridge() {return cBridge;}
	private final Class<EC> cEc; public Class<EC> getClassEntity() {return cEc;}
	private final Class<INT> cInt; public Class<INT> getClassInterval() {return cInt;}
	private final Class<DATA> cData;  public Class<DATA> getClassData() {return cData;}
	private final Class<POINT> cPoint;  public Class<POINT> getClassPoint() {return cPoint;}
	private final Class<WS> cWs; public Class<WS> getClassWorkspace() {return cWs;}
	
	public TsFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<CAT> cCategory, final Class<SCOPE> cScope,
							final Class<ST> cScopeType, final Class<UNIT> cUnit, final Class<MP> cMp,
							final Class<TS> cTs, final Class<TRANSACTION> cTransaction, final Class<SOURCE> cSource, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<INT> cInt,
							final Class<DATA> cData, final Class<POINT> cPoint,
							final Class<WS> cWs)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cScope=cScope;
		this.cScopeType=cScopeType;
		this.cUnit=cUnit;
		this.cTs=cTs;
		this.cMp=cMp;
        this.cTransaction=cTransaction;
        this.cSource=cSource;
        this.cBridge=cBridge;
        this.cEc=cEc;
        this.cInt=cInt;
        this.cData=cData;
        this.cPoint=cPoint;
        this.cWs=cWs;
	}
	
	public EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> ts()
	{
		return new EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cTs);
	}
	
	public EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> scope()
	{
		return new EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cScope);
	}
	
	public EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> bridge()
	{
		return new EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cBridge);
	}
	
	public EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> transaction()
	{
		return new EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cTransaction);
	}
	
	public EjbTsDataFactory<TS,TRANSACTION,DATA,WS> data() {return new EjbTsDataFactory<TS,TRANSACTION,DATA,WS>(cData);}
	
	public EjbTsDataPointFactory<MP,DATA,POINT> ejbDataPoint()
	{
		return new EjbTsDataPointFactory<MP,DATA,POINT>(cPoint);
	}
	
	public EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> entityClass()
	{
		return new EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cEc);
	}
	
	public EjbTsMutliPointFactory<L,D,CAT,SCOPE,ST,UNIT,MP,EC,INT> ejbMultiPoint()
	{
		return new EjbTsMutliPointFactory<L,D,CAT,SCOPE,ST,UNIT,MP,EC,INT>(cMp);
	}
	
	public McTimeSeriesFactory<SCOPE,MP,TS,BRIDGE,EC,INT,DATA,POINT,WS> metaChart(JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs)
	{
		return new McTimeSeriesFactory<SCOPE,MP,TS,BRIDGE,EC,INT,DATA,POINT,WS>(this,fTs);
	}
	
	public Comparator<SCOPE> cmpScope(TsScopeComparator.Type type)
	{
		return (new TsScopeComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>()).factory(TsScopeComparator.Type.position);
	}
}