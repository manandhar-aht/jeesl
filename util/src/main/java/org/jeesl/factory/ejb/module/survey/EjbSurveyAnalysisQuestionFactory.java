package org.jeesl.factory.ejb.module.survey;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSurveyAnalysisQuestionFactory<L extends UtilsLang, D extends UtilsDescription,
				QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,?,?,?,?>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyAnalysisQuestionFactory.class);
	
	private final Class<AQ> cAq;
	
	public EjbSurveyAnalysisQuestionFactory(final Class<AQ> cAq)
	{       
        this.cAq = cAq;
	}
    
	public AQ build(ANALYSIS analysis, QUESTION question)
	{
		AQ ejb = null;
		try
		{
			ejb = cAq.newInstance();
			ejb.setAnalysis(analysis);
			ejb.setQuestion(question);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}