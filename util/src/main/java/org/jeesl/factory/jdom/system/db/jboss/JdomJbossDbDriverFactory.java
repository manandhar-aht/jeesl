package org.jeesl.factory.jdom.system.db.jboss;

import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdomJbossDbDriverFactory
{
	final static Logger logger = LoggerFactory.getLogger(JdomJbossDbDriverFactory.class);
	
	public static Element postgres()
	{		
		return build("org.postgresql","org.postgresql","org.postgresql.xa.PGXADataSource");
	}
	
	public static Element mysql()
	{		
		return build("com.mysql","com.mysql","com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
	}
	
	private static Element build(String name, String module, String xa)
	{
		Element e = driver(name);
		e.setAttribute("module", module);
		
		if(xa!=null)
		{
			Element eXa = new Element("xa-datasource-class");
			eXa.addContent(xa);
			e.getChildren().add(eXa);
		}
		
		return e;
	}
	
	private static Element driver(String name)
	{
		Element e = new Element("driver");
		e.setAttribute("name", name);
		return e;
	}
}