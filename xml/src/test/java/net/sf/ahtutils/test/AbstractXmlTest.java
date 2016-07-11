package net.sf.ahtutils.test;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.model.xml.JeeslNsPrefixMapper;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlTest <T extends Object> extends AbstractAhtUtilsXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTest.class);
	
	public AbstractXmlTest(){this(null,null);}
	public AbstractXmlTest(Class<T> cXml,String xmlDirSuffix)
	{
		super(cXml,xmlDirSuffix);
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("config.ahtutils-xml.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initJaxb()
	{
		JaxbUtil.setNsPrefixMapper(new JeeslNsPrefixMapper());
		DateUtil.ignoreTimeZone=true;
	}
}