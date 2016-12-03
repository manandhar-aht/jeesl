package org.jeesl.factory.xml.system.symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Color;

public class XmlColorFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlColorFactory.class);
		
	
	public static <E extends Enum<E>> Color build(E group, String value){return build(group.toString(),value);}
	public static Color build(String group, String value)
	{
		Color xml = new Color();
		xml.setGroup(group);
		xml.setValue(value);
		return xml;
	}
}