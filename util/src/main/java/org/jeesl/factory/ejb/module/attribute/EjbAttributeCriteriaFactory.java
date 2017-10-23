package org.jeesl.factory.ejb.module.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbAttributeCriteriaFactory<L extends UtilsLang, D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeCriteriaFactory.class);
	
	private final Class<CRITERIA> cCriteria;
    
	public EjbAttributeCriteriaFactory(final Class<CRITERIA> cCriteria)
	{       
        this.cCriteria = cCriteria;
	}
    
	public CRITERIA build(CATEGORY category, TYPE type)
	{
		CRITERIA ejb = null;
		try
		{
			ejb = cCriteria.newInstance();
			ejb.setCategory(category);
			ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}