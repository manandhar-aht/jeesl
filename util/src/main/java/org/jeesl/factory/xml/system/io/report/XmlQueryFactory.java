package org.jeesl.factory.xml.system.io.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Query;

public class XmlQueryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlQueryFactory.class);
	
	public static <E extends Enum<E>> Query build(E type, String value){return build(type.toString(),value);}
	private static Query build(String type, String value)
	{
		Query xml = new Query();
		xml.setType(type);
		xml.setValue(value);
		return xml;
	}
}