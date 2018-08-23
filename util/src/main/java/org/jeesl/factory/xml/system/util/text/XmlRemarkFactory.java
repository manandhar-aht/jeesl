package org.jeesl.factory.xml.system.util.text;

import java.util.Map;

import org.jeesl.model.xml.text.Remark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlRemarkFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRemarkFactory.class);
	
	private final String localeCode;
	
	public XmlRemarkFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public  Remark build(Map<String,L> map)
	{
		if(map.containsKey(localeCode))
		{
			return build(map.get(localeCode).getLang());
		}
		return build("--no-translation--");
	}
	
	public static Remark build(String value) {return build(null,null,value);}
	
	public static <E extends Enum<E>> Remark build(E key, String value){return build(key.toString(),value);}
	public static Remark build(String key,String value) {return build(key,null,value);}
	
	public static Remark build(String key,Integer version,String value)
	{
		Remark xml = new Remark();
		if(key!=null){xml.setKey(key);}
		if(version!=null){xml.setVersion(version);}
		xml.setValue(value);
		return xml;
	}
}