package org.jeesl.client.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.api.rest.system.io.db.JeeslDbDumpRest;
import org.jeesl.client.JeeslBootstrap;
import org.jeesl.controller.processor.system.io.db.DatabaseBackupProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.util.cli.UtilsCliOption;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.interfaces.util.ConfigKey;

public class JeeslDbBackupNotifier
{
	final static Logger logger = LoggerFactory.getLogger(JeeslDbBackupNotifier.class);
	
	private static final String keyDumpDir = "db.dir.dump";
	
	private UtilsCliOption jco;
	private Option oUrl;

	
	public JeeslDbBackupNotifier()
	{
		
		
	}
	
	private void createOptions()
	{
		jco.buildHelp();
		jco.buildDebug();
        
        oUrl = Option.builder("url").required(true).hasArg(true).argName("URL").desc("URL").build(); jco.getOptions().addOption(oUrl);
	}
	
	public void parseArguments(UtilsCliOption jco, String args[]) throws Exception
	{
		this.jco = jco;
		
		createOptions();
		
		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(jco.getOptions() , args); 
	     
		jco.handleHelp(line);
		jco.handleLogger(line);
		
//		Configuration config = uOption.initConfig(line, MeisBootstrap.xmlConfig);
	    
		String url = line.getOptionValue(oUrl.getOpt());
	}
	
	public void local()
	{	
		Configuration config = JeeslBootstrap.init();
		
		
		String url = config.getString(ConfigKey.netRestUrl);
		File fDirectory = new File(config.getString("dir.db.backup"));
		
		logger.info("URL: "+url);
		logger.info("Directory: "+fDirectory.getAbsolutePath());
		
		DatabaseBackupProcessor processor = new DatabaseBackupProcessor(buildRest(url),fDirectory,"x");
		processor.upload();
		
	}
	
	private JeeslDbDumpRest buildRest(String url)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget restTarget = client.target(url);
		return restTarget.proxy(JeeslDbDumpRest.class);
	}
	
	public static void main(String args[]) throws FileNotFoundException, UtilsConfigurationException, NamingException, ExlpConfigurationException
	{
		JeeslDbBackupNotifier notifier = new JeeslDbBackupNotifier();
		notifier.local();
		
//		JaxbUtil.setNsPrefixMapper(new MeisNsPrefixMapper());
		
		UtilsCliOption jco = new UtilsCliOption(org.jeesl.Version.class.getPackage().getImplementationVersion());
		jco.setLog4jPaths("jeesl/client/config");
		
		try
		{
			
			notifier.parseArguments(jco,args);
		}
		catch (ParseException e) {logger.error(e.getMessage());jco.help();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (ExlpUnsupportedOsException e) {e.printStackTrace();}
		catch (NamingException e) {e.printStackTrace();}
		catch (SQLException e) {e.printStackTrace();}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		catch (UtilsLockingException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
	}
}