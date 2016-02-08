package net.sf.ahtutils.factory.ejb.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class EjbSecurityActionFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityActionFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<C> clCategory;
    final Class<R> clRole;
    final Class<V> clView;
    final Class<U> clUsecase;
    final Class<A> clAction;
    final Class<USER> clUser;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			  AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
    	EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
    {
        return new EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
    }
    
    public EjbSecurityActionFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategory = clCategory;
        this.clRole = clRole;
        this.clView = clView;
        this.clUsecase = clUsecase;
        this.clAction = clAction;
        this.clUser = clUser;
    } 
    
    public A create(V view, String code)
    {
    	A ejb = null;
    	
    	try
    	{
			ejb = clAction.newInstance();
			ejb.setPosition(1);
			ejb.setView(view);
			ejb.setCode(code);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}