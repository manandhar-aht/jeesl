package org.jeesl.factory.ejb.system.io.fr;

import java.util.Date;
import java.util.UUID;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoFrMetaFactory<CONTAINER extends JeeslFileContainer<?,META>, META extends JeeslFileMeta<CONTAINER,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoFrMetaFactory.class);
	
	private final Class<META> cMeta;
    
	public EjbIoFrMetaFactory(final Class<META> cMeta)
	{       
        this.cMeta = cMeta;
	}
	
	public META build(CONTAINER container, String name, long size, Date record)
	{
		META ejb = null;
		try
		{
			 ejb = cMeta.newInstance();
			 ejb.setContainer(container);
			 ejb.setCode(UUID.randomUUID().toString());
			 ejb.setName(name);
			 ejb.setSize(size);
			 ejb.setRecord(record);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}