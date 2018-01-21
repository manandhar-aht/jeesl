package org.jeesl.factory.json.module.survey;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.model.json.module.survey.JsonSurveyValue;
import org.jeesl.model.json.module.survey.JsonSurveyValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSurveyValuesFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyValuesFactory.class);
	
	public static JsonSurveyValues build(){return new JsonSurveyValues();}
	
	public static List<JsonSurveyValue> buildBooleanList()
	{
		List<JsonSurveyValue> list = new ArrayList<JsonSurveyValue>();
		list.add(JsonSurveyValueFactory.build(true));
		list.add(JsonSurveyValueFactory.build(false));
		return list;
	}
}