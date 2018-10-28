package org.jeesl.web.rest.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.pojo.UtilsCredential;
import net.sf.ahtutils.web.rest.auth.RestBasicAuthenticator;

public abstract class JeeslRestSecurityInterceptor
{
	final static Logger logger = LoggerFactory.getLogger(JeeslRestSecurityInterceptor.class);
	
    public boolean isBasicAuthentication(String authorizationHeader)
    {
    	return authorizationHeader.toUpperCase().startsWith(RestBasicAuthenticator.BASIC);

    }
    
    protected UtilsCredential decodeBasicAuthentication(String authorizationHeader)
    {
    	return RestBasicAuthenticator.decode(authorizationHeader);
    }
}