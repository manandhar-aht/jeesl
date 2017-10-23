package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveySchemeFactory<
				SCHEME extends JeeslSurveyScheme<?,?,TEMPLATE,?>,
				TEMPLATE extends JeeslSurveyTemplate<?,?,SCHEME,TEMPLATE,?,?,?,?,?,?>
				>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveySchemeFactory.class);
	
	final Class<SCHEME> cScheme;
    
	public EjbSurveySchemeFactory(final Class<SCHEME> cScheme)
	{       
        this.cScheme = cScheme;
	}
	    
	public SCHEME build(TEMPLATE template, String code, List<SCHEME> list)
	{
		SCHEME ejb = null;
		try
		{
			ejb = cScheme.newInstance();
			ejb.setTemplate(template);
			ejb.setCode(code);
			if(list!=null){ejb.setPosition(list.size()+1);}else {ejb.setPosition(1);}

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}