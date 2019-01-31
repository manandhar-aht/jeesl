package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyValidationFactory<QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?,?,?>,
										VALIDATION extends JeeslSurveyValidation<?,?,QUESTION,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyValidationFactory.class);

	final Class<VALIDATION> cValidation;
    
	public EjbSurveyValidationFactory(final Class<VALIDATION> cValidation)
	{       
        this.cValidation = cValidation;
	}
   	
	
	public VALIDATION build(QUESTION question, List<VALIDATION> list)
	{
		VALIDATION ejb = null;
		try
		{
			ejb = cValidation.newInstance();
			ejb.setQuestion(question);
			EjbPositionFactory.next(ejb, list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}