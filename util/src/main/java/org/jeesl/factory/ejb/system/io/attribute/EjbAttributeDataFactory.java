package org.jeesl.factory.ejb.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAttributeDataFactory<CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,
									CONTAINER extends JeeslAttributeContainer<?,DATA>,
									DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeDataFactory.class);
	
	private final Class<DATA> cData;
    
	public EjbAttributeDataFactory(final Class<DATA> cData)
	{       
        this.cData = cData;
	}
    
	public DATA build(CONTAINER container, CRITERIA criteria)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setCriteria(criteria);
			ejb.setContainer(container);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}