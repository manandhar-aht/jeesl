package net.sf.ahtutils.factory.xml.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.audit.Scope;

public class XmlScopeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlScopeFactory.class);
	
	public static Scope build(int id, String cl)
	{
		Scope xml = new Scope();
		xml.setId(id);
		xml.setClazz(cl);
		return xml;
	}
	
	public static Scope build()
	{
		Scope xml = new Scope();
		return xml;
	}
}