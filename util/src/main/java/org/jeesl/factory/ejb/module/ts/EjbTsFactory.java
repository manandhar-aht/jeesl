package org.jeesl.factory.ejb.module.ts;

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

public class EjbTsFactory<L extends UtilsLang,D extends UtilsDescription,
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
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsFactory.class);
	
	final Class<TS> cTs;
    
	public EjbTsFactory(final Class<TS> cTs)
	{       
        this.cTs=cTs;
	}
	
	public TS build(SCOPE scope, INT interval, BRIDGE bridge)
	{
		TS ejb = null;
		try
		{
			ejb = cTs.newInstance();
			ejb.setScope(scope);
			ejb.setInterval(interval);
			ejb.setBridge(bridge);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}