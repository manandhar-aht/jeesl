package org.jeesl.factory.xml.system.util.text;

import org.jeesl.model.xml.text.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDescriptionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionFactory.class);
	
	public static Description build(String value) {return build(null,null,value);}
	
	public static <E extends Enum<E>> Description build(E key, String value){return build(key.toString(),value);}
	public static Description build(String key,String value) {return build(key,null,value);}
	
	public static Description build(String key,Integer version,String value)
	{
		Description xml = new Description();
		if(key!=null){xml.setKey(key);}
		if(version!=null){xml.setVersion(version);}
		xml.setValue(value);
		return xml;
	}
}