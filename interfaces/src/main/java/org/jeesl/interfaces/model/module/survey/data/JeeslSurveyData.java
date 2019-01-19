package org.jeesl.interfaces.model.module.survey.data;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslSurveyData<L extends UtilsLang, D extends UtilsDescription,
									SURVEY extends JeeslSurvey<L,D,?,?,?>,
									ANSWER extends JeeslSurveyAnswer<L,D,?,?,?,?>,
									CORRELATION extends JeeslSurveyCorrelation<L,D,?>>
			extends Serializable,EjbWithId,EjbWithRecord
{
	public enum Attributes{correlation,survey}
	
	SURVEY getSurvey();
	void setSurvey(SURVEY survey);
	
	CORRELATION getCorrelation();
	void setCorrelation(CORRELATION correlation);
	
	List<ANSWER> getAnswers();
	void setAnswers(List<ANSWER> answers);
}