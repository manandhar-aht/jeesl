package org.jeesl.factory.json.system.survey;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.model.json.survey.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonOptionFactory<OPTION extends JeeslSurveyOption<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonOptionFactory.class);
	
	private final String localeCode;
	private final Option q;
	
	public JsonOptionFactory(String localeCode, Option q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public Option build(OPTION ejb)
	{
		Option json = build();
		
		json.setId(ejb.getId());
		json.setPosition(ejb.getPosition());
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetLabel()) {json.setLabel(ejb.getName().get(localeCode).getLang());}
		if(q.isSetDescription()) {json.setDescription(ejb.getDescription().get(localeCode).getLang());}
		
		if(q.isSetCell()) {json.setCell(ejb.getCell());}
		if(q.isSetRow()) {json.setRow(ejb.getRow());}
		if(q.isSetColumn()) {json.setColumn(ejb.getCol());}
		
		return json;
	}
	
	public static Option build() {return new Option();}
	public static Option id(long id)
	{
		Option json = build();
		json.setId(0);
		return json;
	}
}