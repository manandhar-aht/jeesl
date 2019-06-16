package org.jeesl.client.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
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
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRest;
import org.jeesl.client.JeeslBootstrap;
import org.jeesl.factory.txt.system.io.mail.core.TxtMailFactory;
import org.jeesl.mail.smtp.TextMailSender;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
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
import net.sf.exlp.util.xml.JaxbUtil;

public class JeeslMailSpooler
{
	final static Logger logger = LoggerFactory.getLogger(JeeslMailSpooler.class);
	
	private JeeslIoMailRest rest;
	private TextMailSender smtp;
	
	private UtilsCliOption jco;
	private Option oUrl,oSmtp;

	private String cfgUrl,cfgSmtp;
	
	public JeeslMailSpooler()
	{
		
		
	}
	
	private void buildRest(String url)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget restTarget = client.target(url);
		rest = restTarget.proxy(JeeslIoMailRest.class);
	}
	
	private void buildSmtp(String smtpHost)
	{
		smtp = new TextMailSender(smtpHost);
		smtp.setSmtpDebug(false);
//		smtp.tlsPasswordAuthentication(config.getString(ConfigKey.netSmtpUser),config.getString(ConfigKey.netSmtpPwd));
//		templateMailer.plainPasswordAuthentication(config.getString(ConfigKey.netSmtpUser),config.getString(ConfigKey.netSmtpPwd));
		smtp.debugSettings();
	}
	
	public void local()
	{	
		Configuration config = JeeslBootstrap.init();
		
		cfgUrl = config.getString(ConfigKey.netRestUrl);
		cfgSmtp = config.getString(ConfigKey.netSmtpHost);
		
		debugConfig();
		buildRest(cfgUrl);
		buildSmtp(cfgSmtp);
		
//		spooler();
		localShow(false);
	}
	
	public void localShow(boolean confirm)
	{
		Mails mails = rest.spool();
		for(Mail mail : mails.getMail())
		{
			JaxbUtil.info(mail);
			if(confirm) {rest.confirm(mail.getId());}
		}
	}
	
	private void createOptions()
	{
		jco.buildHelp();
		jco.buildDebug();
        
        oUrl = Option.builder("url").required(true).hasArg(true).argName("URL").desc("URL Endpoint").build(); jco.getOptions().addOption(oUrl);
        oSmtp = Option.builder("smtp").required(true).hasArg(true).argName("HOST").desc("SMTP HOST (at the moment, without auth").build(); jco.getOptions().addOption(oSmtp);
	}
	
	private void debugConfig()
	{
		logger.info("URL: "+cfgUrl);
		logger.info("SMTP: "+cfgSmtp);
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
	    
		cfgUrl = line.getOptionValue(oUrl.getOpt());
		cfgSmtp = line.getOptionValue(oSmtp.getOpt());
		
		debugConfig();
		buildRest(cfgUrl);
		buildSmtp(cfgSmtp);
		spooler();
	}
	
	public void spooler()
	{
		Integer queue;
		while(true)
		{
			int sleepMs = 1;
			try
			{
				queue = spool();
				if(queue==0){sleepMs = 60*1000;}
				logger.info("Queue:"+queue+" Sleeping:"+sleepMs);
			}
			catch (Exception e1) {sleepMs = 60*1000;}
			try{Thread.sleep(sleepMs);}catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	private Integer spool() throws Exception
	{
		Mails mails = rest.spool();
		for(Mail mail : mails.getMail())
		{
			logger.info(TxtMailFactory.debug(mail));
			JaxbUtil.info(mail);
			try
			{
				smtp.send(mail);
				rest.confirm(mail.getId());
			}
			catch (MessagingException e) {e.printStackTrace();}
		}
		if(mails.isSetQueue()){return mails.getQueue();}
		else {return Integer.MAX_VALUE;}
	}
	
	public static void main(String args[]) throws FileNotFoundException, UtilsConfigurationException, NamingException, ExlpConfigurationException
	{
		JeeslMailSpooler notifier = new JeeslMailSpooler();
		
//		notifier.local(); System.exit(-1);
		
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