package org.jeesl.factory.json.module.survey;

import org.jeesl.model.json.survey.Surveys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSurveysFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveysFactory.class);
				
	public static Surveys build()
	{
		Surveys json = new Surveys();	
		return json;
	}
}