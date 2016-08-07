package org.jeesl.factory.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Hash;

public class XmlHashFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlHashFactory.class);
		
	public static Hash create(String hash)
	{
		Hash xml = new Hash();
		xml.setValue(hash);
		return xml;
		
	}
}