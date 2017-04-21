package org.jeesl.factory.ejb.module.log;

import org.jeesl.interfaces.model.module.log.JeeslLog;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbLogItemFactory<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<L,D,LOG,ITEM,IMPACT,SCOPE,USER>,
								ITEM extends JeeslLogItem<L,D,LOG,ITEM,IMPACT,SCOPE,USER>,
								IMPACT extends UtilsStatus<IMPACT,L,D>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								USER extends EjbWithId
								>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLogItemFactory.class);
	
	private final Class<ITEM> cItem;
    
	public EjbLogItemFactory(final Class<ITEM> cItem)
	{  
        this.cItem = cItem;
	}
	    
	public ITEM build()
	{
		ITEM ejb = null;
		try
		{
			ejb = cItem.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}