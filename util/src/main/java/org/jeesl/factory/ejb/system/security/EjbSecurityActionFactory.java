package org.jeesl.factory.ejb.system.security;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityActionFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										 A extends JeeslSecurityAction<L,D,R,V,U,AT>,
										 AT extends JeeslSecurityTemplate<L,D,C>,
										 USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityActionFactory.class);
    
    private final Class<A> cAction;
    
    public EjbSecurityActionFactory(final Class<A> cAction)
    {
        this.cAction = cAction;
    } 
    
    public A build(V view, String code, List<A> list)
    {
    	A ejb = null;
    	
    	try
    	{
			ejb = cAction.newInstance();
			
			ejb.setView(view);
			ejb.setCode(code);
			if(list==null){ejb.setPosition(1);}
			else{ejb.setPosition(list.size()+1);}
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}