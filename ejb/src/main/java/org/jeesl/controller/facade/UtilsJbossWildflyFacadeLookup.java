package org.jeesl.controller.facade;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.jboss.sasl.JBossSaslProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.security.Security;
import java.util.Hashtable;
import java.util.Properties;

public class UtilsJbossWildflyFacadeLookup
{
	final static Logger logger = LoggerFactory.getLogger(UtilsJbossWildflyFacadeLookup.class);

	private String appName;
	private String moduleName;
	private final Properties properties = new Properties();

	public UtilsJbossWildflyFacadeLookup(String appName, String moduleName)
	{
		this(appName,moduleName,null,4447,null,null);
	}
	public UtilsJbossWildflyFacadeLookup(String appName, String moduleName, String host)
	{
		this(appName,moduleName,host,4447,null,null);
	}
	public UtilsJbossWildflyFacadeLookup(String appName, String moduleName, String host, String username, String password)
	{
		this(appName,moduleName,host,4447,username,password);
	}
	public UtilsJbossWildflyFacadeLookup(String appName, String moduleName, String host, int port, String username, String password)
	{
		this.appName=appName;
		this.moduleName=moduleName;
		
		Security.addProvider(new JBossSaslProvider());
		
		properties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
		properties.put("remote.connections", "default");

		if(host==null){host="localhost";}
		logger.info("Connecting to "+host);
		properties.put("remote.connection.default.host", host);
		properties.put("remote.connection.default.port", Integer.valueOf(port).toString());

		properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
		if(username!=null){properties.put("remote.connection.default.username", username);}
		if(password!=null){properties.put("remote.connection.default.password", password);}
		
		EJBClientConfiguration clientConfiguration = new PropertiesBasedEJBClientConfiguration(properties);
		ContextSelector<EJBClientContext> contextSelector = new ConfigBasedEJBClientContextSelector(clientConfiguration);

		EJBClientContext.setSelector(contextSelector);
	}
	   
	@SuppressWarnings("unchecked")
	public <F extends Object> F lookup(Class<F> facade) throws NamingException
    {
        final Context context = createContext(properties);
        
        final String distinctName = "";
 
        final String beanName = facade.getSimpleName()+"Bean";
        final String viewClassName = facade.getName();
        
        StringBuilder sb = new StringBuilder();
        sb.append("ejb:");
        sb.append(appName).append("/");
        sb.append(moduleName).append("/");
        sb.append(distinctName).append("/");
        sb.append(beanName);
        sb.append("!").append(viewClassName);
        logger.trace("Trying: "+sb.toString());
        return (F) context.lookup(sb.toString());
    }
   
   private Context createContext(Properties properties) throws NamingException
   {
		Hashtable<String,String> jndiProperties = new Hashtable<String,String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, properties.getProperty("remote.connection.default.username"));
		jndiProperties.put(Context.SECURITY_CREDENTIALS, properties.getProperty("remote.connection.default.password"));
		String url = "remote://"+ properties.getProperty("remote.connection.default.host")+":"+  properties.getProperty("remote.connection.default.port");

		logger.debug(url);
		jndiProperties.put(Context.PROVIDER_URL, url);
		return new InitialContext(jndiProperties);
   }
}