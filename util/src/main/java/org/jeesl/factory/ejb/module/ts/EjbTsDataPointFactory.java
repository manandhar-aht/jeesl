package org.jeesl.factory.ejb.module.ts;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbTsDataPointFactory<MP extends JeeslTsMultiPoint<?,?,?,?>,
								TS extends JeeslTimeSeries<?,?,?>,
								TRANSACTION extends JeeslTsTransaction<?,DATA,?>,
								DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
								POINT extends JeeslTsDataPoint<DATA,MP>,
								WS extends UtilsStatus<WS,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsDataPointFactory.class);
	
	private final Class<POINT> cPoint;
    
	public EjbTsDataPointFactory(final Class<POINT> cPoint)
	{       
        this.cPoint=cPoint;
	}
	
	public POINT build(DATA data, MP multiPoint, Double value)
	{
		POINT ejb = null;
		try
		{
			ejb = cPoint.newInstance();
			ejb.setData(data);
//			ejb.set
			ejb.setValue(value);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}