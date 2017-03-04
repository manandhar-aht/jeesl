package org.jeesl.interfaces.model.survey;

import org.jeesl.interfaces.model.JeeslWithId;

public interface JeeslSimpleSurveyAnswer extends JeeslWithId
{
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
	
	Double getValueDouble();
	void setValueDouble(Double valueDouble);
	
	Integer getValueNumber();
	void setValueNumber(Integer valueNumber);
	
	String getValueText();
	void setValueText(String valueText);
		
	Double getScore();
	void setScore(Double score);
	
	String getRemark();
	void setRemark(String remark);
}