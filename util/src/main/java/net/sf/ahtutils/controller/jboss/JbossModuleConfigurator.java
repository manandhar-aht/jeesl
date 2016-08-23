package net.sf.ahtutils.controller.jboss;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.sf.ahtutils.controller.util.MavenArtifactResolver;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JbossModuleConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(JbossModuleConfigurator.class);
	
	private static final String srcBaseDir = "listing.aht-utils/admin/installation/jboss/modules";
	
	public static enum Product {eap}
	
	private MultiResourceLoader mrl;
	
	private Product product;
	private String version;
	private File jbossBaseDir;
	
	public JbossModuleConfigurator(Product product, String version,String jbossDir)
	{
		this.product=product;
		this.version=version;
		jbossBaseDir = new File(jbossDir);
		mrl = new MultiResourceLoader();
	}
	
	public void postgres() throws IOException
	{
		File postgresBase = buildMobuleBase("org/postgresql");
		File moduleMain = new File(postgresBase,"main");
		File moduleXml = new File(moduleMain,"module.xml");
		
		if(!postgresBase.exists()){postgresBase.mkdir();}
		if(!moduleMain.exists()){moduleMain.mkdir();}
		if(!moduleXml.exists())
		{
			String src = srcBaseDir+"/"+product+"/"+version+"/postgres.xml";
			logger.info("Available?"+mrl.isAvailable(src)+" "+src);
			InputStream input = mrl.searchIs(src);
			FileUtils.copyInputStreamToFile(input, moduleXml);
		}
		
		if(version.equals("6.3"))
		{
			FileUtils.copyFileToDirectory(MavenArtifactResolver.resolve("org.postgis:postgis-jdbc:1.5.3"),moduleMain);
			FileUtils.copyFileToDirectory(MavenArtifactResolver.resolve("org.postgresql:postgresql:9.4.1209"),moduleMain);
		}
	}
	
	public void mysql() throws IOException
	{
		File baseDir = buildMobuleBase("com/mysql");
		File moduleMain = new File(baseDir,"main");
		File moduleXml = new File(moduleMain,"module.xml");
		
		if(!baseDir.exists()){baseDir.mkdir();}
		if(!moduleMain.exists()){moduleMain.mkdir();}
		if(!moduleXml.exists())
		{
			String src = srcBaseDir+"/"+product+"/"+version+"/mysql.xml";
			logger.info("Available?"+mrl.isAvailable(src)+" "+src);
			InputStream input = mrl.searchIs(src);
			FileUtils.copyInputStreamToFile(input, moduleXml);
		}
		
		if(version.equals("6.3"))
		{
			FileUtils.copyFileToDirectory(MavenArtifactResolver.resolve("mysql:mysql-connector-java:5.1.18"),moduleMain);
		}
	}
	
	public void envers() throws IOException
	{
		File hibernateBase = buildMobuleBase("org/hibernate/envers");
		File moduleMain = new File(hibernateBase,"main");
		File moduleXml = new File(moduleMain,"module.xml");
		
		if(!hibernateBase.exists()){hibernateBase.mkdir();}
		if(!moduleMain.exists()){moduleMain.mkdir();}
		
		String src = srcBaseDir+"/"+product+"/"+version+"/envers.xml";
		InputStream input = mrl.searchIs(src);
		FileUtils.copyInputStreamToFile(input, moduleXml);
		
		if(version.equals("6.3"))
		{
			FileUtils.copyFileToDirectory(MavenArtifactResolver.resolve("org.hibernate:hibernate-envers:4.2.0.Final"),moduleMain);
			
		}
	}
	
	public void hibernate() throws IOException
	{
		File hibernateBase = buildMobuleBase("org/hibernate");
		File moduleMain = new File(hibernateBase,"main");
		File moduleXml = new File(moduleMain,"module.xml");
		
		if(!hibernateBase.exists()){hibernateBase.mkdir();}
		if(!moduleMain.exists()){moduleMain.mkdir();}
		
		String src = srcBaseDir+"/"+product+"/"+version+"/hibernate.xml";
		InputStream input = mrl.searchIs(src);
		FileUtils.copyInputStreamToFile(input, moduleXml);
		
		if(version.equals("6.3"))
		{
			FileUtils.copyFileToDirectory(MavenArtifactResolver.resolve("com.vividsolutions:jts:1.12"),moduleMain);
			FileUtils.copyFileToDirectory(MavenArtifactResolver.resolve("org.hibernate:hibernate-spatial:4.0-M1"),moduleMain);
		}
	}
	
	private File buildMobuleBase(String packageId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("modules/system/layers/base/");
		sb.append(packageId);
		return new File(jbossBaseDir,sb.toString());
	}
}
