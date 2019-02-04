package org.jeesl.interfaces.controller.processor;

import java.io.Serializable;

import org.jeesl.interfaces.model.json.module.survey.SurveyValidatorConfiguration;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;

public interface SurveyValidator <ANSWER extends JeeslSurveyAnswer<?,?,?,?,?,?>> extends Serializable
{
	void init(SurveyValidatorConfiguration config);
	
	boolean validate(ANSWER answer);
}
