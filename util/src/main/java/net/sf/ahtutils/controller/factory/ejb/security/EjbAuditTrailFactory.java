package  net.sf.ahtutils.controller.factory.ejb.security;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.util.UtilsAuditTrail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EjbAuditTrailFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
							 			 T extends UtilsAuditTrail<L,D,C,R,V,U,A,USER,TY>,
							 			 TY extends UtilsStatus<TY,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAuditTrailFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<C> clCategory;
    final Class<R> clRole;
    final Class<V> clView;
    final Class<U> clUsecase;
    final Class<A> clAction;
    final Class<USER> clUser;
    final Class<T> clTrail;
    final Class<TY> clType;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	 			  USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
	 			   T extends UtilsAuditTrail<L,D,C,R,V,U,A,USER,TY>,
	 			   TY extends UtilsStatus<TY,L,D>>
    	EjbAuditTrailFactory<L,D,C,R,V,U,A,USER,T,TY> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser, final Class<T> clTrail,final Class<TY> clType)
    {
        return new EjbAuditTrailFactory<L,D,C,R,V,U,A,USER,T,TY>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser,clTrail,clType);
    }
    
    public EjbAuditTrailFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser, final Class<T> clTrail,final Class<TY> clType)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategory = clCategory;
        this.clRole = clRole;
        this.clView = clView;
        this.clUsecase = clUsecase;
        this.clAction = clAction;
        this.clUser = clUser;
        this.clTrail = clTrail;
        this.clType = clType;
    } 
    
    public T create(USER user, TY type)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTrail.newInstance();
			ejb.setUser(user);
			ejb.setType(type);
			ejb.setRecord(new Date());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}