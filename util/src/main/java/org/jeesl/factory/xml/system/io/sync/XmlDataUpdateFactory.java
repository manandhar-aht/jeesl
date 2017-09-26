package org.jeesl.factory.xml.system.io.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.sync.Result;

public class XmlDataUpdateFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataUpdateFactory.class);
		
	public static DataUpdate build()
	{
		DataUpdate xml = new DataUpdate();
		return xml;
	}
	
	public static DataUpdate build(Result result)
	{
		DataUpdate xml = build();
		xml.setResult(result);
		return xml;
	}
}