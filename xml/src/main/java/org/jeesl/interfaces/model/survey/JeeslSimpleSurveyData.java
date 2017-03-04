package org.jeesl.interfaces.model.survey;

import java.util.Date;

import org.jeesl.interfaces.model.JeeslWithId;

public interface JeeslSimpleSurveyData extends JeeslWithId
{
	public Date getRecord();
	public void setRecord(Date record);
}