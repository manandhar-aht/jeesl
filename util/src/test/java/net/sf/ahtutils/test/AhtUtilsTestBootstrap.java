package net.sf.ahtutils.test;

import java.io.File;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.jeesl.model.xml.JeeslNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhtUtilsTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(AhtUtilsTestBootstrap.class);
	
	public static Configuration init()
	{
		AbstractAhtUtilTest.setfTarget(new File("target"));
		
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("config.ahtutils-util.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new JeeslNsPrefixMapper());
		
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile("utils","util").getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {logger.debug("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" because "+e.getMessage());}

		Configuration config = ConfigLoader.init();
		
		logger.debug("Config and Logger initialized");
		return config;
	}
}