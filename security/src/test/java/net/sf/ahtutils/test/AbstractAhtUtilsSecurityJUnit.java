package net.sf.ahtutils.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.model.xml.JeeslNsPrefixMapper;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsSecurityJUnit extends AbstractJeeslTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsSecurityJUnit.class);
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ahtutils-security.test");
			loggerInit.init();
		}
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new JeeslNsPrefixMapper());
	}
}