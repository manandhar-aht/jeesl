package org.jeesl.interfaces.controller.builder;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;

public interface SurveyCorrelationInfoBuilder<CORRELATION extends JeeslSurveyCorrelation<?>>
{
	void init(CORRELATION c);
	int getDataFields();
	String get(int i);
}