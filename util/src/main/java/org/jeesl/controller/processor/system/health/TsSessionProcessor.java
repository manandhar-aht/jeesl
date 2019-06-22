package org.jeesl.controller.processor.system.health;

import java.util.Date;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.processor.module.ts.AbstractTimeSeriesProcessor;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.bean.system.JeeslSessionRegistryBean;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.joda.time.DateTime;
import org.metachart.xml.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TsSessionProcessor<SYSTEM extends JeeslIoSsiSystem,
									USER extends JeeslUser<?>,
									SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
									MP extends JeeslTsMultiPoint<?,?,SCOPE,?>,
									TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
									TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
									BRIDGE extends JeeslTsBridge<EC>,
									EC extends JeeslTsEntityClass<?,?,?>,
									INT extends UtilsStatus<INT,?,?>,
									DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
									POINT extends JeeslTsDataPoint<DATA,MP>,
									WS extends UtilsStatus<WS,?,?>>
	extends AbstractTimeSeriesProcessor<SCOPE,MP,TS,TRANSACTION,BRIDGE,EC,INT,DATA,POINT,WS>
{
	final static Logger logger = LoggerFactory.getLogger(TsSessionProcessor.class);
	
	public TsSessionProcessor(TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,POINT,?,?,WS,?> fbTs,
									JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,DATA,POINT,?,?,WS,?> fTs)
	{
		super(fbTs,fTs);
	}
		
	public void update(SYSTEM system, JeeslSessionRegistryBean<USER> bSession)
	{
		try
		{
			DateTime dt = new DateTime(new Date());
			Date date = dt.withMillisOfSecond(0).withSecondOfMinute(0).toDate();
			
			TS ts = fcTs(system);
			TRANSACTION transaction = fTs.save(fbTs.transaction().build(null,null));
			
			DATA data = efData.build(ws, ts, transaction, date, null);
			data = fTs.save(data);
					
			for(MP mp : fTs.allForParent(fbTs.getClassMp(), scope))
			{
				if(mp.getCode().equals("active"))
				{
					POINT dp =  efPoint.build(data, mp, Integer.valueOf(bSession.activeSessions()).doubleValue());
					fTs.save(dp);
				}
				else if(mp.getCode().equals("authenticated"))
				{
					POINT dp =  efPoint.build(data, mp, Integer.valueOf(bSession.authenticatedSessions()).doubleValue());
					fTs.save(dp);
				}
			}
			
		}
		catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
	}
	
	public Chart build(String localeCode, Date begin, Date end, SYSTEM system) 
	{
		Chart chart = mfTs.build(localeCode);
		chart.setSubtitle(null);
		try
		{
			chart.setDs(mfTs.multiPoint(localeCode,system,begin,end));
		}
		catch (UtilsNotFoundException e) {}
		return chart;
	}
}