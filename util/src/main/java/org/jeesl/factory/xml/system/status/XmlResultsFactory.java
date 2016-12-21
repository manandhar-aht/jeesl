package org.jeesl.factory.xml.system.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Result;
import net.sf.ahtutils.xml.status.Results;

public class XmlResultsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlResultsFactory.class);
		
	public static Results build()
	{
		Results xml = new Results();

		return xml;
	}
	
	public static Results build(Result result)
	{
		Results xml = new Results();
		xml.getResult().add(result);
		return xml;
	}
}