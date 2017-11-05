package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityRoleFactory <C extends JeeslSecurityCategory<?,?>,
										 R extends JeeslSecurityRole<?,?,C,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityRoleFactory.class);
	
    private final Class<R> cRole;
    
    public EjbSecurityRoleFactory(final Class<R> cRole)
    {
        this.cRole = cRole;
    } 
    
    public R build(C category, String code)
    {
    	R ejb = null;
    	
    	try
    	{
			ejb = cRole.newInstance();
			ejb.setPosition(1);
			ejb.setCategory(category);
			ejb.setCode(code);
			ejb.setVisible(true);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}