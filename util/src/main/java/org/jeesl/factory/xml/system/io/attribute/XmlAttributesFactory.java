package org.jeesl.factory.xml.system.io.attribute;

import org.jeesl.model.xml.system.io.attribute.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAttributesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAttributesFactory.class);
	
	public static Attributes build()
	{
		return new Attributes();
	}
}