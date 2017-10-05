package org.jeesl.factory.xml.system.navigation;

import org.jeesl.model.xml.system.navigation.Breadcrumb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBreadcrumbFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlBreadcrumbFactory.class);
	
	public static Breadcrumb build() {return new Breadcrumb();}
}