package org.jeesl.factory.xml.system.io.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Queries;

public class XmlQueriesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlQueriesFactory.class);
	
	public static Queries build()
	{
		Queries xml = new Queries();

		return xml;
	}
}