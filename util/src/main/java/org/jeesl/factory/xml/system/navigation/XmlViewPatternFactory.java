package org.jeesl.factory.xml.system.navigation;

import org.jeesl.model.xml.system.navigation.ViewPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewPatternFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewPatternFactory.class);
		
	public static ViewPattern build(String value)
	{
		ViewPattern xml = build();
		xml.setValue(value);
		return xml;
	}
	
	public static ViewPattern build()
	{
		return new ViewPattern();
	}
}