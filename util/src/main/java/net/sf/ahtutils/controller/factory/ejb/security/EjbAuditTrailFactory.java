package  net.sf.ahtutils.controller.factory.ejb.security;

import java.util.Date;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.util.UtilsAuditTrail;


public class EjbAuditTrailFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
							 			 T extends UtilsAuditTrail<L,D,C,R,V,U,A,AT,USER,TY>,
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
	 			   C extends JeeslSecurityCategory<L,D>,
	 			   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends JeeslSecurityView<L,D,C,R,U,A>,
	 			   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			  AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			  USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
	 			   T extends UtilsAuditTrail<L,D,C,R,V,U,A,AT,USER,TY>,
	 			   TY extends UtilsStatus<TY,L,D>>
    	EjbAuditTrailFactory<L,D,C,R,V,U,A,AT,USER,T,TY> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<USER> clUser, final Class<T> clTrail,final Class<TY> clType)
    {
        return new EjbAuditTrailFactory<L,D,C,R,V,U,A,AT,USER,T,TY>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser,clTrail,clType);
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