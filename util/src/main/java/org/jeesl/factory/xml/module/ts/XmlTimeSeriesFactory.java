package org.jeesl.factory.xml.module.ts;

import org.jeesl.model.xml.module.ts.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTimeSeriesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTimeSeriesFactory.class);
	
	public static TimeSeries build()
	{
		TimeSeries xml = new TimeSeries();
		
		return xml;
	}
}