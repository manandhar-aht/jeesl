package org.jeesl.factory.ejb.system.security;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityActionFactory <V extends JeeslSecurityView<?,?,?,?,?,A>,
										 A extends JeeslSecurityAction<?,?,?,V,?,?>>
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