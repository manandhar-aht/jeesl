package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbSurveyConditionFactory<QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?>,
										CONDITION extends JeeslSurveyCondition<QUESTION,QE,?>,
										QE extends UtilsStatus<QE,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyConditionFactory.class);

	final Class<CONDITION> cCondition;
    
	public EjbSurveyConditionFactory(final Class<CONDITION> cCondition)
	{       
        this.cCondition = cCondition;
	}
   	
	
	public CONDITION build(QUESTION question, QE element, QUESTION triggerQuestion, List<CONDITION> list)
	{
		CONDITION ejb = null;
		try
		{
			ejb = cCondition.newInstance();
			ejb.setQuestion(question);
			ejb.setElement(element);
			ejb.setTriggerQuestion(triggerQuestion);
			EjbPositionFactory.next(ejb, list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}