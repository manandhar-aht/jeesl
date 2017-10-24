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
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
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
			else{ejb.setPosition(list.size());}
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}