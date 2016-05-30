package net.sf.ahtutils.factory.xml.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.text.Solution;

public class XmlSolutionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSolutionFactory.class);
	
	public static Solution build(String value) {return build(null,null,value);}
	
	public static <E extends Enum<E>> Solution build(E key, String value){return build(key.toString(),value);}
	public static Solution build(String key,String value) {return build(key,null,value);}
	
	public static Solution build(String key,Integer version,String value)
	{
		Solution xml = new Solution();
		if(key!=null){xml.setKey(key);}
		if(version!=null){xml.setVersion(version);}
		xml.setValue(value);
		return xml;
	}
}