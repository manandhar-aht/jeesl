package net.sf.ahtutils.db.shell.postgres;

import java.io.File;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;

public class PostgresDump extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresDump.class);
	String db;
	public PostgresDump(Configuration config)
	{
		super(config,UtilsDbShell.Operation.dump);
		
		pDbShell = XmlParameterFactory.build(UtilsDbShell.cfgBinDump, "Shell command for dump", false);
		try{pDbShell.setValue(config.getString(pDbShell.getKey()));}
		catch (NoSuchElementException e){pDbShell.setValue("pg_dump");}
		configurationParamter.getParameter().add(pDbShell);
	}
	
	/**
	 * added 2nd constructor for different db's
	 * @config normal config file
	 * @db String for db declaration / wis=mopwh
	 */
	public PostgresDump(Configuration config, String db)
	{
	    this(config);
	    this.db=db; 	
	}
	
	public void writeShell() throws ExlpUnsupportedOsException
	{
		operation=UtilsDbShell.Operation.dump;
		buildCommands(true);
		this.save();
	}
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{
		super.cmdPre();
//		if (db=="wis") {dumpDatabaseWis();} else
		dumpDatabase();
		super.cmdPost();
	}
	
	private String dumpDatabase()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" --create");
		sb.append(" --blobs");
		sb.append(" --format=c");
		sb.append(" --verbose");
		sb.append(" --file=").append(pDirDump.getValue()+File.separator+pDbName.getValue()+".sql");
		sb.append(" ").append(pDbName.getValue());
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	/**
	 *  added extra options to dump wis db for restoring geo*tables+seq correctly 
	 */
	private String dumpDatabaseWis()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" --quote-all-identifiers");
		sb.append(" --blobs");
		sb.append(" --format=c");
		sb.append(" --role \"postgres\"");
		sb.append(" --section=pre-data");
		sb.append(" --section=data");
		sb.append(" --section=post-data");
		sb.append(" --oids");
		sb.append(" --use-set-session-authorization");
		sb.append(" --encoding \"UTF8\"");
		sb.append(" --verbose");
		sb.append(" --file=").append(pDirDump.getValue()+File.separator+pDbName.getValue()+".sql");
		sb.append(" ").append(pDbName.getValue());
		
		super.addLine(sb.toString());
		return sb.toString();
	}
}
