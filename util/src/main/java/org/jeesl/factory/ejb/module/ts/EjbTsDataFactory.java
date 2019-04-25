package org.jeesl.factory.ejb.module.ts;

import java.util.Date;

import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.model.xml.module.ts.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbTsDataFactory<TS extends JeeslTimeSeries<?,?,?>,
								TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
								DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
								WS extends UtilsStatus<WS,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsDataFactory.class);
	
	private final Class<DATA> cData;
    
	public EjbTsDataFactory(final Class<DATA> cData)
	{       
        this.cData=cData;
	}
	
	public DATA build(WS workspace, TS timeSeries, TRANSACTION transaction, Data data)
	{
		if (data.isSetValue())
		{
			return build(workspace,timeSeries,transaction,data.getRecord().toGregorianCalendar().getTime(),data.getValue());
		}
		else
		{
			return build(workspace,timeSeries,transaction,data.getRecord().toGregorianCalendar().getTime(),null);
		}
	}
	
	public DATA build(WS workspace, TS timeSeries, TRANSACTION transaction, Date date, Double value)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setWorkspace(workspace);
			ejb.setTimeSeries(timeSeries);
			ejb.setTransaction(transaction);
			ejb.setRecord(date);
			ejb.setValue(value);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}