package org.jeesl.factory.ejb.module.ts;

import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsMultiPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTsDataPointFactory<MP extends JeeslTsMultiPoint<?,?,?,?>,
								DATA extends JeeslTsData<?,?,?,?>,
								POINT extends JeeslTsDataPoint<DATA,MP>>
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
			ejb.setMultiPoint(multiPoint);
			ejb.setValue(value);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}