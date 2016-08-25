package org.jeesl.factory.xml.system.security;

import net.sf.ahtutils.xml.security.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSecurityFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSecurityFactory.class);
		
	public static Security build()
	{
		return new Security();
	}
	
}