package org.jeesl.factory.json.module.survey;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.model.json.survey.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSurveyOptionFactory<OPTION extends JeeslSurveyOption<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyOptionFactory.class);
	
	private final String localeCode;
	private final Option q;
	
	public JsonSurveyOptionFactory(String localeCode, Option q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public Option build(OPTION ejb)
	{
		Option json = build();
		
		if(q.getId()!=null) {json.setId(ejb.getId());}
		if(q.getPosition()!=null) {json.setPosition(ejb.getPosition());}
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
		json.setId(id);
		return json;
	}
	public static Option id(String code, String lable, String description)
	{
		Option json = build();
		json.setCode(code);
		json.setLabel(lable);
		json.setDescription(description);
				
		return json;
	}
	
}