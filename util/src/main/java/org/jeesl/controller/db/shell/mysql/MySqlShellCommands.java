package org.jeesl.controller.db.shell.mysql;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

public class MySqlShellCommands
{
	final static Logger logger = LoggerFactory.getLogger(MySqlShellCommands.class);
	
	public static String dropDatabase(String user, String db) throws ExlpUnsupportedOsException {return dropDatabase(user, "", db);}
	
	public static String dropDatabase(String user, String RootPwd, String db) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -p").append(RootPwd);
							sb.append(" -e \"DROP DATABASE IF EXISTS ").append(db).append(";\"");
							;break;
			case OsX: 	 sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -e \"DROP DATABASE IF EXISTS ").append(db).append(";\"");
							;break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String createDatabase(String user, String db) throws ExlpUnsupportedOsException {return createDatabase(user, "", db);}
	
	public static String createDatabase(String user, String RootPwd, String db) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:   sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -p").append(RootPwd);
							sb.append("  -e \"CREATE DATABASE ").append(db).append(" CHARSET utf8;\"");
			;break;
			case OsX: 	 sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -e \"CREATE DATABASE ").append(db).append(" CHARSET utf8;\"");
							;break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String grantDatabase(String user, String dbName, String dbUser, String dbPwd) throws ExlpUnsupportedOsException {return grantDatabase(user, "", dbName, dbUser,dbPwd);}
	
	public static String grantDatabase(String user, String RootPwd, String dbName, String dbUser, String dbPwd) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32: sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -p").append(RootPwd);			
							sb.append(" -e \"GRANT all ON ").append(dbName).append(".* TO ").append(dbUser).append("@localhost IDENTIFIED by '").append(dbPwd).append("';\"");
							break;
			case OsX: 	 sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -e \"GRANT all ON ").append(dbName).append(".* TO ").append(dbUser).append("@localhost IDENTIFIED by '").append(dbPwd).append("';\"");
							break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String restoreDatabase(String user, String dbName, String dbFile) throws ExlpUnsupportedOsException {return restoreDatabase(user, "", dbName, dbFile);}
	
	public static String restoreDatabase(String user, String RootPwd, String dbName, String dbFile) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" -p").append(RootPwd);
							sb.append(" ").append(dbName);
							sb.append(" < \"").append(dbFile).append("\"");
			break;
			case OsX: 	 sb.append("mysql");
							sb.append(" -u ").append(user);
							sb.append(" ").append(dbName);
							sb.append(" < \"").append(dbFile).append("\"");
							break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static void work() throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/mysql", "-u root", "-e \"DROP DATABASE IF EXISTS ofxdev;\"");
		Map<String, String> env = pb.environment();
		
		
		for(String kex : env.keySet())
		{
//			logger.info(kex+" "+env.get(kex));
		}
//		env.put("VAR1", "myValue");
//		env.remove("OTHERVAR");
//		env.put("VAR2", env.get("VAR1") + "suffix");
//		pb.directory(new File("/usr/local/bin"));
		File log = new File("log");

		Process p = pb.start();
		
		logger.info(IOUtils.toString(p.getErrorStream()));
	}

}