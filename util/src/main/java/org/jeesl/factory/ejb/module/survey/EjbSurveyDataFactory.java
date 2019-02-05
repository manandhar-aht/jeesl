package org.jeesl.factory.ejb.module.survey;

import java.util.Date;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyDataFactory<SURVEY extends JeeslSurvey<?,?,?,?,DATA>,
				DATA extends JeeslSurveyData<?,?,SURVEY,?,CORRELATION>,
				CORRELATION extends JeeslSurveyCorrelation<DATA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyDataFactory.class);
	
	final Class<DATA> cData;
    
	public EjbSurveyDataFactory(final Class<DATA> cData)
	{       
        this.cData = cData;
	}
		
	public DATA build(SURVEY survey, CORRELATION correlation)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setSurvey(survey);
			ejb.setCorrelation(correlation);
			ejb.setRecord(new Date());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}