package org.jeesl.factory.json.system.survey;

import org.jeesl.factory.json.system.status.JsonStatusFactory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.model.json.survey.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JsonSurveyFactory<L extends UtilsLang,D extends UtilsDescription, SURVEY extends JeeslSurvey<L,D,SS,?,?>, SS extends UtilsStatus<SS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyFactory.class);
	
	private final String localeCode;
	private final Survey q;
	private JsonStatusFactory<SS,L,D> jfStatus;
	
	public JsonSurveyFactory(String localeCode, Survey q)
	{
		this.localeCode=localeCode;
		this.q=q;
		if(q.getStatus()!=null){jfStatus = new JsonStatusFactory<SS,L,D>(localeCode,q.getStatus());}
	}
	
	public Survey build(SURVEY survey)
	{
		Survey json = build();
		if(q.getId()!=0){json.setId(survey.getId());}
		if(q.getLabel()!=null){json.setLabel(survey.getName().get(localeCode).getLang());}
		if(q.getDateStart()!=null){json.setDateStart(survey.getStartDate());}
		if(q.getDateEnd()!=null){json.setDateEnd(survey.getEndDate());}
		if(q.getStatus()!=null){json.setStatus(jfStatus.build(survey.getStatus()));}
		return json;
	}
	
	public static Survey build()
	{
		Survey json = new Survey();	
		return json;
	}
}