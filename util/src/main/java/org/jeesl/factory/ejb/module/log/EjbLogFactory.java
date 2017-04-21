package org.jeesl.factory.ejb.module.log;

import org.jeesl.interfaces.model.module.log.JeeslLog;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbLogFactory<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<L,D,LOG,ITEM,IMPACT,SCOPE>,
								ITEM extends JeeslLogItem<L,D,LOG,ITEM,IMPACT,SCOPE>,
								IMPACT extends UtilsStatus<IMPACT,L,D>,
								SCOPE extends UtilsStatus<SCOPE,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLogFactory.class);
	
	private final Class<LOG> cLog;
    
	public EjbLogFactory(final Class<LOG> cLog)
	{  
        this.cLog = cLog;
	}
	    
	public LOG build()
	{
		LOG ejb = null;
		try
		{
			ejb = cLog.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}