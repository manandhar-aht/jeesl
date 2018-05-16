package org.jeesl.factory.ejb.system.io.domain;

import java.util.List;

import org.jeesl.interfaces.model.system.io.domain.JeeslDomainItem;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDomainItemFactory<QUERY extends JeeslDomainQuery<?,?,?,?>,
									SET extends JeeslDomainSet<?,?,?>,
									ITEM extends JeeslDomainItem<QUERY,SET>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDomainItemFactory.class);
	
	private final Class<ITEM> cItem;
    
	public EjbDomainItemFactory(final Class<ITEM> cItem)
	{       
        this.cItem = cItem;
	}
    
	public ITEM build(SET set, QUERY query, List<ITEM> list)
	{
		ITEM ejb = null;
		try
		{
			ejb = cItem.newInstance();
			ejb.setItemSet(set);
			ejb.setQuery(query);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}