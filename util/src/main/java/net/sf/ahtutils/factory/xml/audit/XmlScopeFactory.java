package net.sf.ahtutils.factory.xml.audit;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.audit.Change;
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
	
	public static Scope build(List<Change> changes)
	{
		Scope scope = new Scope();
    	scope.getChange().addAll(changes);
    	return scope;
	}
}