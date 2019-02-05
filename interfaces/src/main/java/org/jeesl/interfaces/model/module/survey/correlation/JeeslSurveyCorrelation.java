package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyCorrelation<DATA extends JeeslSurveyData<?,?,?,?,?>>
			extends Serializable,EjbWithId,EjbSaveable
{
//	DATA getData();
//	void setData(DATA data);
}