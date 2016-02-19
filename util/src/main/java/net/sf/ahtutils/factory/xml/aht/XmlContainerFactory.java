package net.sf.ahtutils.factory.xml.aht;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.aht.Container;

public class XmlContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlContainerFactory.class);
	
	public static Container build()
	{
		Container xml = new Container();
		return xml;
	}
}