package org.jeesl.factory.xml.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.ts.TimeSeries;

public class XmlTimeSeriesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTimeSeriesFactory.class);
	
	public static TimeSeries build()
	{
		TimeSeries xml = new TimeSeries();
		
		return xml;
	}
}