package org.jeesl.factory.xml.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Access;

public class XmlAccessFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAccessFactory.class);

	public static Access build(Boolean publicUser, Boolean authenticatedUser)
	{
		Access xml = new Access();
		if(publicUser!=null){xml.setPublicUser(publicUser);}else{xml.setPublicUser(false);}
		if(authenticatedUser!=null){xml.setAuthenticatedUser(authenticatedUser);}else{xml.setAuthenticatedUser(false);}
		return xml;
	}
}