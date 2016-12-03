package org.jeesl.factory.xml.system.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Style;
import net.sf.ahtutils.xml.status.Styles;

public class XmlStylesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlStylesFactory.class);
		
	public static Styles build()
	{
		Styles xml = new Styles();

		return xml;
	}
	
	public static Styles build(Style style)
	{
		Styles xml = new Styles();
		xml.getStyle().add(style);
		return xml;
	}
}