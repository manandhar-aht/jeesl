package org.jeesl.factory.ejb.system.security;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityViewFactory <C extends JeeslSecurityCategory<?,?>,
										 V extends JeeslSecurityView<?,?,C,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityViewFactory.class);
	
    private final Class<V> cView;
      
    public EjbSecurityViewFactory(final Class<V> cView)
    {
        this.cView = cView;
    } 
    
    public V build(C category, String code, List<V> list)
    {
    	V ejb = null;
    	
    	try
    	{
			ejb = cView.newInstance();
			ejb.setCategory(category);
			ejb.setCode(code);
			if(list==null){ejb.setPosition(1);}
			else{ejb.setPosition(list.size()+1);}
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}