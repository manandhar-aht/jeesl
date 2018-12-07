package org.jeesl.factory.ejb.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyScoreFactory<QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,SCORE,?,?,?,?>,
									SCORE extends JeeslSurveyScore<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyScoreFactory.class);
	
	final Class<SCORE> cScore;
    
	public EjbSurveyScoreFactory(final Class<SCORE> cScore)
	{       
        this.cScore = cScore;
	}
	    
	public SCORE build(QUESTION question)
	{
		SCORE ejb = null;
		try
		{
			ejb = cScore.newInstance();

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}