package org.jeesl.factory.ejb.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbAttributeSetFactory<L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
									ITEM extends JeeslAttributeItem<?,SET>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeSetFactory.class);
	
	private final Class<SET> cSet;
    
	public EjbAttributeSetFactory(final Class<SET> cSet)
	{       
        this.cSet = cSet;
	}
    
	public SET build(CATEGORY category)
	{
		SET ejb = null;
		try
		{
			ejb = cSet.newInstance();
			ejb.setCategory(category);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}