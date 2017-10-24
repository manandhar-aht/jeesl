package org.jeesl.interfaces.model.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithSurveyType<SURVEY extends JeeslSurvey<?,?,?,?,?>,
										W extends JeeslWithType<?,?,TYPE>,
										TYPE extends UtilsStatus<TYPE,?,?>>
			extends EjbWithId, JeeslWithSurvey<SURVEY>
{
	public enum Attributes{survey}

}