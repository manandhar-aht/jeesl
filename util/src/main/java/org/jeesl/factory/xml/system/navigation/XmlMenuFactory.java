package org.jeesl.factory.xml.system.navigation;

import org.jeesl.model.xml.system.navigation.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMenuFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMenuFactory.class);
	
	public static Menu build() {return new Menu();}
}