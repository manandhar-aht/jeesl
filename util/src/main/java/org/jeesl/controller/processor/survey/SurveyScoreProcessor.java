package org.jeesl.controller.processor.survey;

import java.util.Map;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;

public class SurveyScoreProcessor <QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?>, ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,?,?>>
{
	public SurveyScoreProcessor()
	{
		
	}
	
	public double score(Map<QUESTION,ANSWER> answers)
	{
		double result = 0;
		for(QUESTION q : answers.keySet())
		{
			if(q.getCalculateScore()!=null && q.getCalculateScore())
			{
				ANSWER a = answers.get(q);
				if(a.getScore()!=null)
				{
					result = result+a.getScore();
				}
			}
		}
		return result;
	}
}