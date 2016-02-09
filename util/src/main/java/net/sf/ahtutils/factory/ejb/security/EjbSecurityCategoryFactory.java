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

public class EjbSecurityCategoryFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityCategoryFactory.class);
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			  AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
    	EjbSecurityCategoryFactory<L,D,C,R,V,U,A,AT,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction, final Class<USER> clUser)
    {
        return new EjbSecurityCategoryFactory<L,D,C,R,V,U,A,AT,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
    }
    
    public EjbSecurityCategoryFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser)
    {
    	super(clLang,clDescription);
        this.cCategory = clCategory;
        this.cRole = clRole;
        this.cView = clView;
        this.cUsecase = clUsecase;
        this.cAction = clAction;
        this.cUser = clUser;
    } 
    
    public C create(String code, String type)
    {
    	C ejb = null;
    	
    	try
    	{
			ejb = cCategory.newInstance();
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