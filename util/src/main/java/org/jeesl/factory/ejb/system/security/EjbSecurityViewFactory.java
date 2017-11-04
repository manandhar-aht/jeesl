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

public class EjbSecurityViewFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
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