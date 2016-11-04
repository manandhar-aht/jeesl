package net.sf.ahtutils.db.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.ahtutils.xml.xpath.DbseedXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;

public class UtilsDbXmlSeedUtil
{
	final static Logger logger = LoggerFactory.getLogger(UtilsDbXmlSeedUtil.class);
	
	public static enum DataSource{ide,jar,path,tmp}
	
	public static String configKeySeed = "db.seed";
	public static String configKeyPathExport = "db.export.path";
	public static String configKeyRestExportUrl = "db.export.url";
	public static String configKeyRestImportUrl = "db.import.url";
	public static String cliDebug = "db.export.cli.debug";
    public static String cliSave = "db.export.cli.save";
	
    private Configuration config;
	protected Db dbSeed;
	private File fTmp;
	
	private String seedPath;
	protected String pathPrefix;
	
	public UtilsDbXmlSeedUtil(Configuration config) throws FileNotFoundException
	{
		this.config=config;
		String dbSeedFile = config.getString(configKeySeed);
		logger.info("Using seed: "+dbSeedFile);
		dbSeed = JaxbUtil.loadJAXB(dbSeedFile, Db.class);
		try{dbSeed.setPathExport(config.getString(configKeyPathExport));} catch (NoSuchElementException e){}
		seedPath=dbSeed.getPathIde();
		
		try{fTmp = new File(config.getString(ConfigKey.dirTmp));}
		catch (NoSuchElementException e){fTmp = new File(System.getProperty("java.io.tmpdir"));}
		
	}
	
	public UtilsDbXmlSeedUtil(Db dbSeed)
	{
		this(dbSeed,DataSource.ide);
	}
	
	public UtilsDbXmlSeedUtil(Db dbSeed, DataSource datasource)
	{
		this.dbSeed=dbSeed;
		seedPath=dbSeed.getPathIde();
	}
	
	public String getContentName(String extractId) throws UtilsConfigurationException
	{
		try
		{
			Seed seed = DbseedXpath.getSeed(dbSeed, extractId);
			return seed.getContent();
		}
		catch (ExlpXpathNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public String getPath(DataSource ds)
	{
		StringBuffer sb = new StringBuffer();
		switch(ds)
		{
			case ide: sb.append(dbSeed.getPathIde());break;
			case path: sb.append(dbSeed.getPathExport());break;
			case tmp: sb.append(fTmp.getAbsolutePath());break;
			case jar: break;
			default: logger.warn("NYI ds="+ds);
		}
		return sb.toString();
	}
	
	public String getExtractName(String extractId) throws UtilsConfigurationException {return getExtractName(DataSource.ide,extractId);}
	public String getExtractName(DataSource ds, String extractId) throws UtilsConfigurationException
	{
		StringBuffer sb = new StringBuffer();
		if(pathPrefix!=null){sb.append(pathPrefix).append("/");}
		
		sb.append(getPath(ds));
		sb.append("/");
		sb.append(getContentName(extractId));
		
		switch(ds)
		{
			case path: checkPath(sb.toString());break;
			case jar: sb = new StringBuffer();sb.append(getContentName(extractId));break;
			case tmp: checkPath(sb.toString());break;
			default: ;
		}
		
		return sb.toString();
	}
	
	public void setPathPrefix(String pathPrefix) {this.pathPrefix = pathPrefix;}
	
	private void checkPath(String fileName) throws UtilsConfigurationException
	{
		if(!dbSeed.isSetPathExport()){throw new UtilsConfigurationException("Config element "+configKeyPathExport+" is not set!");}
		File base = new File(dbSeed.getPathExport());
		if(!base.exists() || !base.isDirectory()){throw new UtilsConfigurationException("Path "+dbSeed.getPathExport()+" does not exist");}
		
		File f = new File(fileName);
		if(!f.getParentFile().exists()){f.getParentFile().mkdirs();}
	}
	
	public void writeXml(DataSource ds, String key, Object o) throws UtilsConfigurationException
	{
		String fileName = getExtractName(ds,key);
		logger.info("Writing "+fileName);
		if(config.getBoolean(cliDebug)){JaxbUtil.info(o);}
		if(config.getBoolean(cliSave)){JaxbUtil.save(new File(fileName), o, true);}
	}
	
	public void writeXml(DataSource ds, String key, long id, Object o) throws UtilsConfigurationException
	{
		String fileName = getExtractName(ds,key)+"/"+id+".xml";
		checkPath(fileName);
		logger.info("Writing "+fileName);
		if(config.getBoolean(cliDebug)){JaxbUtil.info(o);}
		if(config.getBoolean(cliSave)){JaxbUtil.save(new File(fileName), o, true);}
	}
}