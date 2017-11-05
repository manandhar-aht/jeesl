package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityUserFactory <USER extends JeeslUser<?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityUserFactory.class);
	
    final Class<USER> cUser;
    
    public EjbSecurityUserFactory(final Class<USER> cUser)
    {
        this.cUser = cUser;
    } 
    
	public USER build()
	{
		USER ejb = null;
    	
    	try
    	{
			ejb = cUser.newInstance();
			ejb.setPermitLogin(false);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
	}
}