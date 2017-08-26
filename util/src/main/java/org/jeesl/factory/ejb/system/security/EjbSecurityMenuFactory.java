package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityMenuFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 M extends JeeslSecurityMenu<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
						extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityMenuFactory.class);
	
	private final Class<M> cM;
    
    public EjbSecurityMenuFactory(final Class<L> cLang,final Class<D> cDescription, final Class<M> cM)
    {
    	super(cLang,cDescription);
        this.cM = cM;
    } 
    
    public M create(V view)
    {
    	M ejb = null;
    	
    	try
    	{
			ejb = cM.newInstance();
			ejb.setView(view);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}