package net.sf.ahtutils.controller;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.jboss.sasl.JBossSaslProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsJbossFacadeLookup
{
	final static Logger logger = LoggerFactory.getLogger(UtilsJbossFacadeLookup.class);
	
	private String appName;
	private String moduleName;
	private Properties properties = new Properties();
	
	public UtilsJbossFacadeLookup(String appName, String moduleName)
	{
		this(appName,moduleName,null,null,null);
	}
	public UtilsJbossFacadeLookup(String appName, String moduleName, String host)
	{
		this(appName,moduleName,host,null,null);
	}
	public UtilsJbossFacadeLookup(String appName, String moduleName, String host, String username, String password)
	{
		this.appName=appName;
		this.moduleName=moduleName;
		
		Security.addProvider(new JBossSaslProvider());
		
		properties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
		properties.put("remote.connections", "default");

		if(host==null){host="localhost";}
		logger.info("Connecting to "+host);
		properties.put("remote.connection.default.host", host);
		properties.put("remote.connection.default.port", "4447");

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
        final Context context = createContext();
        
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
   
   private Context createContext() throws NamingException
   {
       final Map<String,String> jndiProperties = new HashMap<String,String>();
       jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//       jndiProperties.put(Context.PROVIDER_URL, "remote://" +host +":4447");
//       if(username!=null){jndiProperties.put(Context.SECURITY_PRINCIPAL, username);}
//       if(password!=null){jndiProperties.put(Context.SECURITY_CREDENTIALS, password);}
       return new InitialContext(jndiProperties);
   }
}