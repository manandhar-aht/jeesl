package org.jeesl.factory.jdom;

import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdomElementFactory
{
	final static Logger logger = LoggerFactory.getLogger(JdomElementFactory.class);
	
	public static Element simple(String name, boolean content) {return simple(name,Boolean.valueOf(content).toString());}
	public static Element simple(String name, int content) {return simple(name,Integer.valueOf(content).toString());}
	
	public static Element simple(String name, String content)
	{		
		Element e = build(name);
		e.addContent(content);
		return e;
	}
	
	public static Element attribute(String name, String attribute, String value)
	{		
		Element e = build(name);
		e.setAttribute(attribute, value);
		return e;
	}
	
	public static Element build(String name)
	{		
		Element e = new Element(name);
		return e;
	}
}