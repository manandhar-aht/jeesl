package org.jeesl.factory.ejb.module.survey;

import java.util.List;
import java.util.UUID;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyOptionSetFactory<TEMPLATE extends JeeslSurveyTemplate<?,?,?,TEMPLATE,?,?,?,?,OPTIONS,?>,
										OPTIONS extends JeeslSurveyOptionSet<?,?,TEMPLATE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyOptionSetFactory.class);
	
	final Class<OPTIONS> cOptions;
    
	public EjbSurveyOptionSetFactory(final Class<OPTIONS> cOptions)
	{       
        this.cOptions = cOptions;
	}
	    
	public OPTIONS build(TEMPLATE template, List<OPTIONS> list)
	{
		OPTIONS ejb = null;
		try
		{
			ejb = cOptions.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setTemplate(template);
			
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}