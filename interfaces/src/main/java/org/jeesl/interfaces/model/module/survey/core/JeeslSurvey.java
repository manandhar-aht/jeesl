package org.jeesl.interfaces.model.module.survey.core;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.date.EjbWithDateRange;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.utils.UtilsWithStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurvey<L extends UtilsLang, D extends UtilsDescription,
								SS extends UtilsStatus<SS,L,D>,
								TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>,
								DATA extends JeeslSurveyData<L,D,?,?,?>
>
			extends Serializable,EjbSaveable,EjbWithDateRange,
						UtilsWithStatus<L,D,SS>,EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{id,template,status,startDate,endDate}
	public enum Status{open,preparation};
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	List<DATA> getSurveyData();
	void setSurveyData(List<DATA> data);
}