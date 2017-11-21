package org.jeesl.factory.ejb.system.io.fr;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoFrStorageFactory<STORAGE extends JeeslFileStorage<?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoFrStorageFactory.class);
	
	private final Class<STORAGE> cStorage;
    
	public EjbIoFrStorageFactory(final Class<STORAGE> cStorage)
	{       
        this.cStorage = cStorage;
	}
	
	public STORAGE build()
	{
		STORAGE ejb = null;
		try
		{
			 ejb = cStorage.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}