package org.jeesl.factory.ejb.system.security;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityArea;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityAreaFactory <V extends JeeslSecurityView<?,?,?,?,?,?>,
										AR extends JeeslSecurityArea<?,?,V>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityAreaFactory.class);
	
	private final Class<AR> cAr;
    
    public EjbSecurityAreaFactory(final Class<AR> cAr)
    {
		this.cAr = cAr;
    } 
      
    public AR build(V view, List<AR> list)
    {
    	AR ejb = null;
    	
    	try
    	{
			ejb = cAr.newInstance();
			ejb.setView(view);
			EjbPositionFactory.next(ejb,list);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}