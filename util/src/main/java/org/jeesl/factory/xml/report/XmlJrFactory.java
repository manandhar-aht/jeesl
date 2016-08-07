package org.jeesl.factory.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Jr;

public class XmlJrFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlJrFactory.class);
		
	public static Jr build(String type,String name,boolean visible)
	{
		Jr xml = new Jr();
		xml.setType(type);
		xml.setName(name);
		xml.setVisible(visible);
		return xml;
		
	}
}