package org.jeesl.test;

import java.io.File;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractJeeslTest;
import net.sf.exlp.util.io.LoggerInit;

public class AbstractJeeslTestTest extends AbstractJeeslTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslTestTest.class);
	
	protected File f;
	protected boolean saveReference=false;

	@BeforeClass
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		AbstractJeeslTest.initTargetDirectory();
	}
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("jeesl/test/config");
			loggerInit.init();
		}
    }
}