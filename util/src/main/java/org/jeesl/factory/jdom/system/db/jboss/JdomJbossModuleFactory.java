package org.jeesl.factory.jdom.system.db.jboss;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jeesl.factory.jdom.JdomElementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class JdomJbossModuleFactory
{
	final static Logger logger = LoggerFactory.getLogger(JdomJbossModuleFactory.class);
	
	public static Element mysql()
	{
		Element e = new Element("module");
		e.setAttribute("name", "com.mysql");
		
		Element eResources = new Element("resources");
		eResources.addContent(JdomElementFactory.attribute("resource-root", "path", "mysql-connector-java-5.1.29.jar"));
		e.addContent(eResources);
		
		Element eDependencies = new Element("dependencies");
		eDependencies.addContent(JdomElementFactory.attribute("module", "name", "javax.api"));
		e.addContent(eDependencies);
		
		JDomUtil.setNameSpaceRecursive(e, Namespace.getNamespace("urn:jboss:module:1.0"));
		return e;
	}
}