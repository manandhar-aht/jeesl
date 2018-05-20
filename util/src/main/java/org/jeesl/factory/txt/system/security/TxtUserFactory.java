package org.jeesl.factory.txt.system.security;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtUserFactory <USER extends JeeslUser<?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtUserFactory.class);
    
    public TxtUserFactory()
    {
    	
    } 
    
    public String name(USER user)
    {
	    	StringBuffer sb = new StringBuffer();
	    	sb.append(user.getFirstName());
	    	sb.append(" ");
	    	sb.append(user.getLastName());
	    	return sb.toString();
    }
}