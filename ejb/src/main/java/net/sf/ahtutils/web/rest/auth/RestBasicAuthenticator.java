package net.sf.ahtutils.web.rest.auth;

import java.util.List;

import net.sf.ahtutils.model.pojo.UtilsCredential;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestBasicAuthenticator
{
	final static Logger logger = LoggerFactory.getLogger(RestBasicAuthenticator.class);

	public static UtilsCredential decodeResteasy(HttpRequest request) throws UnauthorizedException
	{
		List<String> header = request.getHttpHeaders().getRequestHeader("Authorization");
	    	if(header!=null)
	    	{
	    		String auth = header.get(0);
	    		if(auth.toUpperCase().startsWith("BASIC "))
	    		{
	    			String base64 = auth.substring("BASIC ".length());
	    			logger.trace("Base64: "+base64);
	        		String token = new String(Base64.decodeBase64(base64));
	            	logger.trace("Authorization: "+token);
	            	
	            	int index = token.indexOf(":");
	            	
	            	return new UtilsCredential(token.substring(0,index),token.substring(index+1));
	    		}
	    		else {throw new UnauthorizedException("We only support BASIC authentication");}
	    	}
	    	else{throw new UnauthorizedException("No Authorization Header available");}
	}
}