package org.jeesl.client;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(JeeslBootstrap.class);
	
	public final static String xmlConfig = "config.ahtutils-ejb.test/ahtutils.xml";
	
	public static Configuration init()
	{
		return init(xmlConfig);
		
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("config.ahtutils-ejb.test");
		loggerInit.init();
						
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile("ahtutils","ejb.test").getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {logger.debug("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" because "+e.getMessage());}
		ConfigLoader.add(configFile);
		
		 return ConfigLoader.init();
	}

}
