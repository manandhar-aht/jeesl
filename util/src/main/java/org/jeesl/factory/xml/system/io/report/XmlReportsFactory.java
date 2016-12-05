package org.jeesl.factory.xml.system.io.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Reports;

public class XmlReportsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlReportsFactory.class);
		
	public static Reports build()
	{
		Reports xml = new Reports();
		
		return xml;
	}
}