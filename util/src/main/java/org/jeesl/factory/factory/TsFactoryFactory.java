package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsClassFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
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
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsFactoryFactory<L extends UtilsLang, D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, 
								BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								SAMPLE extends JeeslTsSample<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								USER extends EjbWithId, 
								WS extends UtilsStatus<WS,L,D>,
								QAF extends UtilsStatus<QAF,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(TsFactoryFactory.class);
	
	private final Class<SCOPE> cScope;
	private final Class<TRANSACTION> cTransaction;
	private final Class<DATA> cData;
	private final Class<BRIDGE> cBridge;
	private final Class<EC> cEc;
    
	public TsFactoryFactory(final Class<SCOPE> cScope, final Class<TRANSACTION> cTransaction, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<DATA> cData)
	{
		this.cScope=cScope;
        this.cTransaction=cTransaction;
        this.cBridge=cBridge;
        this.cData=cData;
        this.cEc=cEc;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
					TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, 
					BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
					EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, SAMPLE extends JeeslTsSample<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>, 
					USER extends EjbWithId, 
					WS extends UtilsStatus<WS,L,D>,
					QAF extends UtilsStatus<QAF,L,D>>
	TsFactoryFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> factory(final Class<SCOPE> cScope, final Class<TRANSACTION> cTransaction, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<DATA> cData)
	{
		return new TsFactoryFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cScope,cTransaction,cBridge,cEc,cData);
	}
	
	public EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> scope()
	{
		return new EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cScope);
	}
	
	public EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> bridge()
	{
		return new EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cBridge);
	}
	
	public EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> transaction()
	{
		return new EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cTransaction);
	}
	
	public EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> data()
	{
		return new EjbTsDataFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cData);
	}
	
	public EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> entityClass()
	{
	return new EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cEc);
	}
}