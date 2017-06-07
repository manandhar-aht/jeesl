package org.jeesl.factory.xml.system.io.sync;

import net.sf.ahtutils.xml.sync.DataUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataUpdateFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataUpdateFactory.class);
		
	public static DataUpdate build()
	{
		DataUpdate xml = new DataUpdate();
		return xml;
	}
}