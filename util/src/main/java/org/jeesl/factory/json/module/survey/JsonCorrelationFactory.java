package org.jeesl.factory.json.module.survey;

import org.jeesl.model.json.survey.Correlation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonCorrelationFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonCorrelationFactory.class);
	
	public static Correlation build(){return new Correlation();}
	
	public static Correlation build(long id)
	{
		Correlation json = build();
		json.setId(id);
		return json;
	}
}