package org.jeesl.maven.goal.eap;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jeesl.controller.config.jboss.JbossConfigurator;
import org.jeesl.controller.config.jboss.JbossModuleConfigurator;
import org.jeesl.controller.db.shell.mysql.MySqlShellCommands;
import org.jeesl.controller.db.shell.postgres.PostgreSqlShellCommands;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;

@Mojo(name="eap71Config")
public class JeeslJbossEap71Configurator extends AbstractMojo
{
	@Parameter(defaultValue = "INFO")
    private String log;
	
	private enum DbType {mysql,postgres}
	private final Set<DbType> setFiles;
	
	public JeeslJbossEap71Configurator()
	{
		setFiles = new HashSet<DbType>();
	}
	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));

    	Configuration config = config();
		try
		{
			dbRestore(config);
		} catch (ExlpUnsupportedOsException e) {throw new MojoExecutionException(e.getMessage());}
		configureEap(config);
    }
    
    private Configuration config()
    {
    	try
		{
			String cfn = ExlpCentralConfigPointer.getFile("jeesl","eapConfig").getAbsolutePath();
			ConfigLoader.add(cfn);
			getLog().info("Using config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {getLog().error("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		
		Configuration config = ConfigLoader.init();					
		return config;
    }
    
    private void configureEap(Configuration config) throws MojoExecutionException
    {
    	String jbossDir = config.getString("eap.dir","/Volumes/ramdisk/jboss");
		File f = new File(jbossDir);
		getLog().info("JBoss EAP 7.1 directoy: "+f.getAbsolutePath());
    	
    	ModelControllerClient client;
    	JbossModuleConfigurator jbossModule = new JbossModuleConfigurator(JbossModuleConfigurator.Product.eap,"7.1",jbossDir);
    	try {client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9990);}
    	catch (UnknownHostException e) {throw new MojoExecutionException(e.getMessage());}
    	
    	JbossConfigurator jbossConfig = new JbossConfigurator(client);
    	
    	String key = config.getString("eap.configurations");
	    getLog().warn("Keys: "+key);
	    String[] keys = key.split("-");
	    
	    try
	    {
	    	dbFiles(keys,config,jbossModule);
	    	dbDrivers(keys,config,jbossConfig);
	    	dbDs(keys,config,jbossConfig);
	    }
	    catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
    
    private void dbRestore(Configuration config) throws ExlpUnsupportedOsException
    {
	    String[] keys = config.getString("db.restores").split("-");
	    for(String key : keys)
    	{
	    	getLog().info("Starting DB Restore for "+key);
	    	String pDbName = config.getString("db."+key+".db");
	    	String pDbUser = config.getString("db."+key+".user");
	    	String pDbPwd = config.getString("db."+key+".pwd");
	    	String pDbDump = config.getString("db."+key+".dump");
	    	String pDbRootPwd = config.getString("db."+key+".rootpwd",null);
        	DbType dbType = DbType.valueOf(config.getString("db."+key+".type"));
        	switch(dbType)
        	{
        		case mysql: System.out.println(MySqlShellCommands.dropDatabase("root",pDbRootPwd,pDbName));
        					System.out.println(MySqlShellCommands.createDatabase("root",pDbRootPwd,pDbName));
        					System.out.println(MySqlShellCommands.grantDatabase("root",pDbRootPwd,pDbName,pDbUser,pDbPwd));
        					System.out.println(MySqlShellCommands.restoreDatabase("root",pDbRootPwd,pDbName,pDbDump));
        					
        					break;
        		case postgres:	System.out.println(PostgreSqlShellCommands.createUser("postgres",pDbUser,pDbPwd));
        						System.out.println(PostgreSqlShellCommands.terminate("postgres",pDbName));
        						System.out.println(PostgreSqlShellCommands.terminate("postgres","template1"));
        						System.out.println(PostgreSqlShellCommands.dropDatabase("postgres",pDbName));
        						System.out.println(PostgreSqlShellCommands.createDatabase("postgres",pDbName,pDbUser));
        						System.out.println(PostgreSqlShellCommands.postGis("postgres",pDbName));
        						System.out.println(PostgreSqlShellCommands.restoreDatabase("postgres", pDbName, pDbDump));
        						
        					break;
        	}
    	}
	
    }
    
    private void dbFiles(String[] keys, Configuration config, JbossModuleConfigurator jbossModule) throws IOException
    {
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
        		case mysql: if(!setFiles.contains(dbType))
        					{
        						jbossModule.mysql();
        						getLog().info("DB: MySQL ... files copied");
        						setFiles.add(dbType);
        					}
        					break;
        		case postgres: if(!setFiles.contains(dbType))
							{
								jbossModule.postgres();
								jbossModule.hibernate();
								getLog().info("DB: PostGIS files copied");
								setFiles.add(dbType);
							}
				break;
        	}
    	}
    }
    
    private void dbDrivers(String[] keys, Configuration config, JbossConfigurator jbossConfig) throws IOException
    {
    	List<String> log = new ArrayList<String>();
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
        		case mysql: if(!jbossConfig.driverExists("mysql"))
        					{
        						jbossConfig.createMysqlDriver();
        						log.add("mysql");
        					}
        					break;
        		case postgres: if(!jbossConfig.driverExists("postgres"))
							{
								jbossConfig.createPostgresDriver();
								log.add("postgres");
							}
							break;
        	}
    	}
    	getLog().info("DB Drivers: "+StringUtils.join(log, ", "));
    
    }
    
    private void dbDs(String[] keys, Configuration config, JbossConfigurator jbossConfig) throws IOException
    {
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	String ds=null;
        	switch(dbType)
        	{
        		case mysql: ds = jbossConfig.createMysqlDatasource(config,key); break;						
        		case postgres: ds = jbossConfig.createPostgresDatasource(config,key);break;
        	}
        	if(ds!=null) {getLog().info("DS: "+ds);}
    	}
    }
}