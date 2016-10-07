package org.jeesl.factory.xml.system.navigation;

import org.jeesl.model.xml.system.navigation.UrlMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUrlMappingFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlUrlMappingFactory.class);
		
	public static UrlMapping build(String value, String base)
	{
		UrlMapping xml = build();
		xml.setValue(value);
		if(base!=null){xml.setUrl(base);}
		return xml;
	}
	
	public static UrlMapping build()
	{
		return new UrlMapping();
	}
}