package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityUsecaseFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityUsecaseFactory.class);
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			  AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
    	EjbSecurityUsecaseFactory<L,D,C,R,V,U,A,AT,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
    {
        return new EjbSecurityUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
    }
    
    public EjbSecurityUsecaseFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
    {
    	super(clLang,clDescription);
        this.cCategory = clCategory;
        this.cRole = clRole;
        this.cView = clView;
        this.cUsecase = clUsecase;
        this.cAction = clAction;
        this.cUser = clUser;
    } 
    
    public U create(C category, String code)
    {
    	U ejb = null;
    	
    	try
    	{
			ejb = cUsecase.newInstance();
			ejb.setCategory(category);
			ejb.setCode(code);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}