package org.jeesl.factory.ejb.system.io.attribute;

import java.util.List;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbAttributeItemFactory<CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,
									SET extends JeeslAttributeSet<?,?,?,ITEM>,
									ITEM extends JeeslAttributeItem<CRITERIA,SET>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeItemFactory.class);
	
	private final Class<ITEM> cItem;
    
	public EjbAttributeItemFactory(final Class<ITEM> cItem)
	{       
        this.cItem = cItem;
	}
    
	public ITEM build(CRITERIA criteria, SET set, List<ITEM> list)
	{
		ITEM ejb = null;
		try
		{
			ejb = cItem.newInstance();
			ejb.setCriteria(criteria);
			ejb.setItemSet(set);
			
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}