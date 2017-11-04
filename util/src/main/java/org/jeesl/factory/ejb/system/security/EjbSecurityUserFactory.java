package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityUserFactory <L extends UtilsLang, D extends UtilsDescription,
									 C extends JeeslSecurityCategory<L,D>,
									 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									 V extends JeeslSecurityView<L,D,C,R,U,A>,
									 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
									 A extends JeeslSecurityAction<L,D,R,V,U,AT>,
									 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
									 USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
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