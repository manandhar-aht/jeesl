package org.jeesl.mail;

import net.sf.exlp.util.io.LoggerInit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJeeslMailTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslMailTest.class);
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit-error.xml");	
			loggerInit.addAltPath("config.ahtutils-test");
			loggerInit.init();
		}
    }
	
	@Test public void dummy() {}
}