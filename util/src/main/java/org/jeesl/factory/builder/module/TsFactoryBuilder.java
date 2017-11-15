package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsClassFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsScopeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsTransactionFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends JeeslTsScope<L,D,CAT,UNIT,EC,INT>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends JeeslTimeSeries<L,D,SCOPE,BRIDGE,INT>,
								TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								SOURCE extends EjbWithLangDescription<L,D>, 
								BRIDGE extends JeeslTsBridge<EC>,
								EC extends JeeslTsEntityClass<L,D,CAT>,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends JeeslTsData<L,D,TS,TRANSACTION,SAMPLE,WS>,
								SAMPLE extends JeeslTsSample,
								USER extends EjbWithId, 
								WS extends UtilsStatus<WS,L,D>,
								QAF extends UtilsStatus<QAF,L,D>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(TsFactoryBuilder.class);
	
	private final Class<CAT> cCategory; public Class<CAT> getClassCategory() {return cCategory;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<UNIT> cUnit; public Class<UNIT> getClassUnit()  {return cUnit;}
	private final Class<TS> cTs; public Class<TS> getClassTs() {return cTs;}
	private final Class<TRANSACTION> cTransaction; public Class<TRANSACTION> getClassTransaction() {return cTransaction;}
	private final Class<SOURCE> cSource; public Class<SOURCE> getClassSource() {return cSource;}
	private final Class<BRIDGE> cBridge; public Class<BRIDGE> getClassBridge() {return cBridge;}
	private final Class<EC> cEc; public Class<EC> getClassEntity() {return cEc;}
	private final Class<INT> cInt; public Class<INT> getClassInterval() {return cInt;}
	private final Class<DATA> cData;  public Class<DATA> getClassData() {return cData;}
	private final Class<WS> cWs; public Class<WS> getClassWorkspace() {return cWs;}
	
	public TsFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<CAT> cCategory, final Class<SCOPE> cScope, final Class<UNIT> cUnit, final Class<TS> cTs, final Class<TRANSACTION> cTransaction, final Class<SOURCE> cSource, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<INT> cInt,final Class<DATA> cData, final Class<WS> cWs)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cScope=cScope;
		this.cUnit=cUnit;
		this.cTs=cTs;		
        this.cTransaction=cTransaction;
        this.cSource=cSource;
        this.cBridge=cBridge;
        this.cEc=cEc;
        this.cInt=cInt;
        this.cData=cData;
        this.cWs=cWs;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends JeeslTsScope<L,D,CAT,UNIT,EC,INT>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends JeeslTimeSeries<L,D,SCOPE,BRIDGE,INT>,
					TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, SOURCE extends EjbWithLangDescription<L,D>, 
					BRIDGE extends JeeslTsBridge<EC>,
					EC extends JeeslTsEntityClass<L,D,CAT>,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends JeeslTsData<L,D,TS,TRANSACTION,SAMPLE,WS>, SAMPLE extends JeeslTsSample, 
					USER extends EjbWithId, 
					WS extends UtilsStatus<WS,L,D>,
					QAF extends UtilsStatus<QAF,L,D>>
	TsFactoryBuilder<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> factory(final Class<L> cL, final Class<D> cD, final Class<CAT> cCategory, final Class<SCOPE> cScope, final Class<UNIT> cUnit, final Class<TS> cTs, final Class<TRANSACTION> cTransaction, final Class<SOURCE> cSource, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<INT> cInt, final Class<DATA> cData, final Class<WS> cWs)
	{
		return new TsFactoryBuilder<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cL,cD,cCategory,cScope,cUnit,cTs,cTransaction,cSource,cBridge,cEc,cInt,cData,cWs);
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
	
	public EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> data()
	{
		return new EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cData);
	}
	
	public EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> entityClass()
	{
	return new EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cEc);
	}
}