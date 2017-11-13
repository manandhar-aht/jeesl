package org.jeesl.interfaces.model.module.survey;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithSurvey<SURVEY extends JeeslSurvey<?,?,?,?,?>>
			extends Serializable,EjbWithId
{
	public enum Attributes{survey}
	
	SURVEY getSurvey();
	void setSurvey(SURVEY survey);
}