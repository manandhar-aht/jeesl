package org.jeesl.factory.xml.system.symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Graphic;

public class XmlGraphicFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlGraphicFactory.class);
			
	public static Graphic build()
	{
		Graphic xml = new Graphic();
		return xml;
	}
}