package net.sf.ahtutils.factory.ejb.security;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityActionFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
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
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
    	EjbSecurityActionFactory<L,D,C,R,V,U,A,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
    {
        return new EjbSecurityActionFactory<L,D,C,R,V,U,A,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
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