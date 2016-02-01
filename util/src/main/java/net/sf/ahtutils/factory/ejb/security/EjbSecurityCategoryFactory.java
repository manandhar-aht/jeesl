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

public class EjbSecurityCategoryFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityCategoryFactory.class);
	
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
    	EjbSecurityCategoryFactory<L,D,C,R,V,U,A,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction, final Class<USER> clUser)
    {
        return new EjbSecurityCategoryFactory<L,D,C,R,V,U,A,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
    }
    
    public EjbSecurityCategoryFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
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
    
    public C create(String code, String type)
    {
    	C ejb = null;
    	
    	try
    	{
			ejb = clCategory.newInstance();
			ejb.setCode(code);
			ejb.setType(type);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}