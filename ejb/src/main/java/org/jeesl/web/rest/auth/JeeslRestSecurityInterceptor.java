package org.jeesl.web.rest.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.pojo.UtilsCredential;

public abstract class JeeslRestSecurityInterceptor
{
	final static Logger logger = LoggerFactory.getLogger(JeeslRestSecurityInterceptor.class);
	
    public boolean isBasicAuthentication(String authorizationHeader)
    {
    	return authorizationHeader.toUpperCase().startsWith(JeeslRestBasicAuthenticator.BASIC);

    }
    
    protected UtilsCredential decodeBasicAuthentication(String authorizationHeader)
    {
    	return JeeslRestBasicAuthenticator.decode(authorizationHeader);
    }
}