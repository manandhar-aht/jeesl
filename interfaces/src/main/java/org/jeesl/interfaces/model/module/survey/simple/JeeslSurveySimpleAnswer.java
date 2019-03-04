package org.jeesl.interfaces.model.module.survey.simple;

import java.io.Serializable;
import java.util.Date;

public interface JeeslSurveySimpleAnswer extends Serializable
{	
	Double getValueDouble();
	void setValueDouble(Double valueDouble);
	
	Integer getValueNumber();
	void setValueNumber(Integer valueNumber);
	
	Date getValueDate();
	void setValueDate(Date valueDate);
	
	String getValueText();
	void setValueText(String valueText);
}