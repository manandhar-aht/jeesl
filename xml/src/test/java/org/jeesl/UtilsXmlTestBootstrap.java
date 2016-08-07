package org.jeesl;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.model.xml.JeeslNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsXmlTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(UtilsXmlTestBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("config.ahtutils-xml.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new JeeslNsPrefixMapper());
        DateUtil.ignoreTimeZone=true;
	}
}