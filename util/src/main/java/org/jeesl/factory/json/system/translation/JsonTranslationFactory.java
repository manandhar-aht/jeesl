package org.jeesl.factory.json.system.translation;

import org.jeesl.model.json.system.translation.JsonTranslation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonTranslationFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonTranslationFactory.class);
	
	public static <E extends Enum<E>> JsonTranslation build(Class<?> c, E attribute, String xpath, String filterBy)
	{
		JsonTranslation json = build(c,attribute,xpath);
		json.setFilterBy(filterBy);
		return json;
	}
	
	public static <E extends Enum<E>> JsonTranslation build(Class<?> c, E attribute, String xpath)
	{
		JsonTranslation json = new JsonTranslation();
		json.setEntity(c.getSimpleName());
		if(attribute!=null) {json.setCode(attribute.toString());}
		json.setXpath(xpath);
		return json;
	}
}