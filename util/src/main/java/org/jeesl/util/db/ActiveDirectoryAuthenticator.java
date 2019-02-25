package org.jeesl.util.db;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveDirectoryAuthenticator
{
	final static Logger logger = LoggerFactory.getLogger(ActiveDirectoryAuthenticator.class);
	
	private String domain;
	private String ldapHost;
	
	public ActiveDirectoryAuthenticator(String domain, String ldapHost)
	{
		this.domain = domain;
		this.ldapHost = ldapHost;
	}
	
	public boolean authenticate(String user, String pass)
	{
		if(pass==null || pass.length()==0){return false;}
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);
		env.put(Context.SECURITY_CREDENTIALS, pass);
		env.put(Context.PROVIDER_URL, ldapHost);

		try
		{
			new InitialLdapContext(env, null);
			return true;
		}
		catch (AuthenticationException e){logger.trace("",e);}
		catch (NamingException e){logger.error("",e);}
		return false;
	}
}
