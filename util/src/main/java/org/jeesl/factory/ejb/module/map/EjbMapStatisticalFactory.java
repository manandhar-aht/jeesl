package org.jeesl.factory.ejb.module.map;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.map.JeeslStatisticalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMapStatisticalFactory<MAP extends JeeslStatisticalMap<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbMapStatisticalFactory.class);
	
	private final Class<MAP> cMap;
	
    public EjbMapStatisticalFactory(final Class<MAP> cMap)
    {
        this.cMap = cMap;
    }
	
	public MAP build(List<MAP> list)
	{
		MAP ejb;
		try
		{
			ejb = cMap.newInstance();
			EjbPositionFactory.next(ejb,list);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}