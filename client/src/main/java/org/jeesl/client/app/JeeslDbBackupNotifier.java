package org.jeesl.client.app;

import java.io.File;
import java.io.FileNotFoundException;

import javax.naming.NamingException;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.api.rest.system.io.db.JeeslDbDumpRest;
import org.jeesl.client.JeeslBootstrap;
import org.jeesl.util.query.xml.FileQuery;
import org.jeesl.util.query.xml.IoQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.dir.DirTreeScanner;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;

public class JeeslDbBackupNotifier
{
	final static Logger logger = LoggerFactory.getLogger(JeeslDbBackupNotifier.class);
	
	private static final String keyDumpDir = "db.dir.dump";
	
	
	private JeeslDbDumpRest rest;
	private Configuration config;
	
	private File fDump;
	
	public JeeslDbBackupNotifier(Configuration config)
	{		
		this.config=config;
		fDump = new File(config.getString(keyDumpDir));
		
		String url = "x";
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget restTarget = client.target(url);
		rest = restTarget.proxy(JeeslDbDumpRest.class);
		logger.info("Checking DumpDirectory "+fDump.toString()+" ("+keyDumpDir+")");
		logger.info("Uploading to "+url);
	}
	
	public void rest()
	{		
		DirTreeScanner dts = new DirTreeScanner(IoQuery.dumpDir());
		Dir dir = dts.getDirTree(fDump,false,FileQuery.sql());
		dir.setCode("xx");
		JaxbUtil.info(dir);
		rest.uploadDumps(dir);
	}
	
	public static void main(String args[]) throws FileNotFoundException, UtilsConfigurationException, NamingException, ExlpConfigurationException
	{
		Configuration config = JeeslBootstrap.init();
		
		JeeslDbBackupNotifier bridge = new JeeslDbBackupNotifier(config);
		bridge.rest();
	}
}