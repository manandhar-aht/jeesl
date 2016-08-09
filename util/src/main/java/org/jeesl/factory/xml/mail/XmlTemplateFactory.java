package org.jeesl.factory.xml.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Template;

public class XmlTemplateFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplateFactory.class);
		
	public static Template build(String lang, String type)
	{
		Template xml = new Template();
		xml.setLang(lang);
		xml.setType(type);
		return xml;
	}
}