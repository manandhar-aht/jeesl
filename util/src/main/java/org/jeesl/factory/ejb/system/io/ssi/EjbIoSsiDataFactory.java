package org.jeesl.factory.ejb.system.io.ssi;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;

public class EjbIoSsiDataFactory <MAPPING extends JeeslIoSsiMapping<?,?>,
									DATA extends JeeslIoSsiData<MAPPING>>
{
	private final Class<DATA> cData;

	public EjbIoSsiDataFactory(final Class<DATA> cData)
	{
        this.cData = cData;
	}
	
	public DATA build(MAPPING mapping)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setMapping(mapping);
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}    
}