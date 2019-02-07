package org.jeesl.interfaces.controller.processor;

import java.io.Serializable;

import org.jeesl.interfaces.model.json.module.survey.SurveyValidatorConfiguration;
import org.jeesl.interfaces.model.module.survey.simple.JeeslSurveySimpleAnswer;

public interface SurveyValidator extends Serializable
{
	void init(SurveyValidatorConfiguration config);
	
	boolean validate(JeeslSurveySimpleAnswer answer);
}
