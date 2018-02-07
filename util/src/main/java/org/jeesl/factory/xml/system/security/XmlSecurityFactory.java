package org.jeesl.factory.xml.system.security;

import org.jeesl.model.xml.system.navigation.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Security;

public class XmlSecurityFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSecurityFactory.class);
		
	public static Security build()
	{
		return new Security();
	}
	
	public static Security build(Menu menu)
	{
		Security xml = build();
		xml.setMenu(menu);

		return xml;
	}
}