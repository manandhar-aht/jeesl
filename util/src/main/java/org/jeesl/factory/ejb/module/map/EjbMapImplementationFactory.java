package org.jeesl.factory.ejb.module.map;

import org.jeesl.interfaces.model.module.map.JeeslStatisticalMap;
import org.jeesl.interfaces.model.module.map.JeeslStatisticalMapImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMapImplementationFactory<MAP extends JeeslStatisticalMap<?,?>,
										 IMP extends JeeslStatisticalMapImplementation<MAP,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbMapImplementationFactory.class);
	
	private final Class<IMP> cImp;
	
    public EjbMapImplementationFactory(final Class<IMP> cImp)
    {
        this.cImp = cImp;
    }
	
	public IMP build(MAP map)
	{
		IMP ejb = null;
		try
		{
			ejb = cImp.newInstance();
			ejb.setMap(map);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
    }
}