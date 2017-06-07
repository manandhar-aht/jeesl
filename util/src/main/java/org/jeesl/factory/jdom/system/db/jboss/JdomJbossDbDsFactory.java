package org.jeesl.factory.jdom.system.db.jboss;

import org.jdom2.Element;
import org.jeesl.factory.jdom.JdomElementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdomJbossDbDsFactory
{
	final static Logger logger = LoggerFactory.getLogger(JdomJbossDbDsFactory.class);
	
	public static Element postgres(String name, String cHost, String cDb, String secUser, String secPwd)
	{		
		Element eDs = ds(name);
		eDs.addContent(connection("postgresql",cHost,cDb));
		eDs.addContent(JdomElementFactory.simple("driver","org.postgresql"));
		eDs.addContent(pool());
		eDs.addContent(security(secUser, secPwd));
		eDs.addContent(validationPostgres());
		return eDs;
	}
	
	public static Element mysql(String name, String cHost, String cDb, String secUser, String secPwd)
	{		
		Element eDs = ds(name);
		eDs.addContent(connection("mysql",cHost,cDb));
		eDs.addContent(JdomElementFactory.simple("driver","com.mysql"));
		eDs.addContent(pool());
		eDs.addContent(security(secUser, secPwd));
		eDs.addContent(statemanetMysql());
		return eDs;
	}
	
	private static Element ds(String name)
	{
		Element e = new Element("datasource");
		e.setAttribute("jndi-name", "java:jboss/datasources/"+name);
		e.setAttribute("pool-name", name);
		e.setAttribute("enabled", "true");
		e.setAttribute("jta", "true");
		e.setAttribute("use-java-context", "true");
		e.setAttribute("use-ccm", "true");		
		
		return e;
	}
	
	private static Element connection(String jdbc, String cHost, String cDb)
	{
		Element e = new Element("connection-url");

		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:").append(jdbc).append("://");
		sb.append(cHost);
		sb.append("/").append(cDb);
		e.addContent(sb.toString());
		
		return e;
	}
	
	private static Element security(String secUser, String secPwd)
	{
		Element e = new Element("security");
		e.addContent(JdomElementFactory.simple("user-name", secUser));
		e.addContent(JdomElementFactory.simple("password", secPwd));
		return e;
	}
	
	private static Element validationPostgres()
	{
		Element e = new Element("validation");
		e.addContent(JdomElementFactory.simple("check-valid-connection-sql", "SELECT 1"));
		e.addContent(JdomElementFactory.simple("validate-on-match",true));
		e.addContent(JdomElementFactory.simple("background-validation", false));
		return e;
	}
	
	private static Element pool()
	{
		Element e = new Element("pool");
		e.addContent(JdomElementFactory.simple("min-pool-size", 10));
		e.addContent(JdomElementFactory.simple("max-pool-size", 100));
		e.addContent(JdomElementFactory.simple("prefill", false));
		e.addContent(JdomElementFactory.simple("use-strict-min", false));
		e.addContent(JdomElementFactory.simple("flush-strategy", "FailingConnectionOnly"));
		return e;
	}
	
	private static Element statemanetMysql()
	{
		Element e = new Element("statement");
		e.addContent(JdomElementFactory.simple("prepared-statement-cache-size", 32));
		e.addContent(JdomElementFactory.build("share-prepared-statements"));
		
		return e;
	}
}