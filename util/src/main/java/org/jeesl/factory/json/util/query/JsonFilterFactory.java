package org.jeesl.factory.json.util.query;

import org.jeesl.model.json.util.query.JsonFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonFilterFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonFilterFactory.class);
	
	public static JsonFilter build() 
	{
		return new JsonFilter();
	}
}