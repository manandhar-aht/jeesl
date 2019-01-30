package org.jeesl.interfaces.model.module.survey.simple;

import java.io.Serializable;

public interface JeeslSurveySimpleAnswer extends Serializable
{	
	Double getValueDouble();
	void setValueDouble(Double valueDouble);
	
	Integer getValueNumber();
	void setValueNumber(Integer valueNumber);
}