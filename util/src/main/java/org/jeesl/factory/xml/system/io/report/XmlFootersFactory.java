package org.jeesl.factory.xml.system.io.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Footers;

public class XmlFootersFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFootersFactory.class);
		
	public static Footers build()
	{
		Footers xml = new Footers();
		return xml;
	}
}