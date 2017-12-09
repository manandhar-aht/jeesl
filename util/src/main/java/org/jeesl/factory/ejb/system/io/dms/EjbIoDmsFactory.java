package org.jeesl.factory.ejb.system.io.dms;

import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoDmsFactory <DMS extends JeeslIoDms<?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoDmsFactory.class);
	
	private final Class<DMS> cDms;

	public EjbIoDmsFactory(final Class<DMS> cDms)
	{
        this.cDms = cDms;
	}
 
	public DMS build()
	{
		DMS ejb = null;
		try
		{
			ejb = cDms.newInstance();

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}