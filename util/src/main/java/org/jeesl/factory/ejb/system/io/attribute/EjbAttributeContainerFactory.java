package org.jeesl.factory.ejb.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAttributeContainerFactory<SET extends JeeslAttributeSet<?,?,?,?>, CONTAINER extends JeeslAttributeContainer<SET,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeContainerFactory.class);
	
	private final Class<CONTAINER> cContainer;
    
	public EjbAttributeContainerFactory(final Class<CONTAINER> cContainer)
	{       
        this.cContainer = cContainer;
	}
    
	public CONTAINER build(SET set)
	{
		CONTAINER ejb = null;
		try
		{
			ejb = cContainer.newInstance();
			ejb.setSet(set);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}