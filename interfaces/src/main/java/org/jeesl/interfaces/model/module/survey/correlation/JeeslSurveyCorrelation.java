package org.jeesl.interfaces.model.module.survey.correlation;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyCorrelation<L extends UtilsLang, D extends UtilsDescription,
											DATA extends JeeslSurveyData<L,D,?,?,?>>
			extends EjbWithId,EjbSaveable
{
//	DATA getData();
//	void setData(DATA data);
}