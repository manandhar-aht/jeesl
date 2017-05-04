package org.jeesl.factory.ejb.module.ts;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbTsBridgeFactory<L extends UtilsLang, D extends UtilsDescription,
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
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsBridgeFactory.class);
	
	final Class<BRIDGE> cBridge;
    
	public EjbTsBridgeFactory(final Class<BRIDGE> cBridge)
	{       
        this.cBridge=cBridge;
	}

	public BRIDGE build(EC entityClass, long refId)
	{
		BRIDGE ejb = null;
		try
		{
			ejb = cBridge.newInstance();
			ejb.setEntityClass(entityClass);
			ejb.setRefId(refId);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}