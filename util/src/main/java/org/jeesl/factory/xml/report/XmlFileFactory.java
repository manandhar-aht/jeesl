package org.jeesl.factory.xml.report;

import net.sf.ahtutils.xml.report.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFileFactory.class);
		
	public static File create(String fileName)
	{
		File xml = new File();
		xml.setValue(fileName);
		return xml;
		
	}
}