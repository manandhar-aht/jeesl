package org.jeesl.interfaces.model.survey;

import org.jeesl.interfaces.model.JeeslWithId;

public interface JeeslSimpleSurveyQuestion extends JeeslWithId
{
	String getQuestion();
	void setQuestion(String question);
}