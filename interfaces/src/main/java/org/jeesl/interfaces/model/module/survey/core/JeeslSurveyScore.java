package org.jeesl.interfaces.model.module.survey.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslSurveyScore<L extends UtilsLang, D extends UtilsDescription,
									SCHEME extends JeeslSurveyScheme<L,D,?,?>,
									QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,?,?,?,?>>
			extends Serializable,EjbSaveable
{
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
	
	SCHEME getScheme();
	void setScheme(SCHEME scheme);
	
	Boolean getCalculateScore();
	void setCalculateScore(Boolean calculateScore);
	
	Double getMinScore();
	void setMinScore(Double minScore);
	
	Double getMaxScore();
	void setMaxScore(Double maxScore);
	
	Boolean getShowScore();
	void setShowScore(Boolean showScore);
}