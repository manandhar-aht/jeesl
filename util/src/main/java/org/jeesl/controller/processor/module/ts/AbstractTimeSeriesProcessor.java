package org.jeesl.controller.processor.module.ts;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractTimeSeriesProcessor<SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
									TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
									TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
									BRIDGE extends JeeslTsBridge<EC>,
									EC extends JeeslTsEntityClass<?,?,?>,
									INT extends UtilsStatus<INT,?,?>,
									DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
									WS extends UtilsStatus<WS,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTimeSeriesProcessor.class);
	
	protected final TsFactoryBuilder<?,?,?,SCOPE,?,?,?,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,?,?,?,WS,?> fbTs;
	
	protected final JeeslTsFacade<?,?,?,SCOPE,?,?,?,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,?,?,?,WS,?> fTs;
	
	protected final EjbTsDataFactory<TS,TRANSACTION,DATA,WS> efData;
	
	protected WS ws;
	protected SCOPE scope;
	protected INT interval;
	protected EC ec;
	
	public AbstractTimeSeriesProcessor(TsFactoryBuilder<?,?,?,SCOPE,?,?,?,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,?,?,?,WS,?> fbTs,
									JeeslTsFacade<?,?,?,SCOPE,?,?,?,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,?,?,?,WS,?> fTs)
	{
		this.fbTs=fbTs;
		this.fTs=fTs;
		efData = fbTs.data();
	}
	
	public <EWS extends Enum<EWS>, ESC extends Enum<ESC>, EIN extends Enum<EIN>> void init(EWS ews, ESC esc, EIN ein, Class<?> c)
	{
		try
		{
			ws = fTs.fByCode(fbTs.getClassWorkspace(),ews);
			scope = fTs.fByCode(fbTs.getClassScope(),esc);
			interval = fTs.fByCode(fbTs.getClassInterval(),ein);
			ec = fTs.fByCode(fbTs.getClassEntity(),c.getName());
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	public void init(WS ws, SCOPE scope, INT interval, EC ec)
	{
		this.ws=ws;
		this.scope=scope;
		this.interval=interval;
		this.ec=ec;
	}
	
	protected boolean isInitialized()
	{
		return (ws!=null) && (scope!=null) && (interval!=null) && (ec!=null);
	}
}