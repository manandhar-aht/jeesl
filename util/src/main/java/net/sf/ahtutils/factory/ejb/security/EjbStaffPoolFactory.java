package  net.sf.ahtutils.factory.ejb.security;

import org.jeesl.factory.ejb.system.security.AbstractEjbSecurityFactory;
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
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaffPool;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;


public class EjbStaffPoolFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										 S extends UtilsStaffPool<L,D,C,R,V,U,A,AT,P,E,USER>,
										 P extends EjbWithId,
										 E extends EjbWithId,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStaffPoolFactory.class);
	
    final Class<S> clStaff;
    final Class<P> clPool;
    final Class<E> clEntity;
    final Class<USER> clUser;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			  AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			   S extends UtilsStaffPool<L,D,C,R,V,U,A,AT,P,E,USER>,
	 			   P extends EjbWithId,
	 			   E extends EjbWithId,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
    	EjbStaffPoolFactory<L,D,C,R,V,U,A,AT,S,P,E,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<S> clStaff, final Class<P> clPool, final Class<E> clEntity,final Class<USER> clUser)
    {
        return new EjbStaffPoolFactory<L,D,C,R,V,U,A,AT,S,P,E,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clStaff,clPool,clEntity,clUser);
    }
    
    public EjbStaffPoolFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<S> clStaff, final Class<P> clPool,final Class<E> clEntity,final Class<USER> clUser)
    {
    	super(clLang,clDescription);
        this.cCategory = clCategory;
        this.cRole = clRole;
        this.cView = clView;
        this.cUsecase = clUsecase;
        this.cAction = clAction;
        this.clStaff = clStaff;
        this.clPool = clPool;
        this.clEntity = clEntity;
        this.clUser = clUser;
    } 
    
    public S create(P pool, R role, E entity)
    {
    	S ejb = null;
    	
    	try
    	{
			ejb = clStaff.newInstance();
			ejb.setPool(pool);
			ejb.setRole(role);
			ejb.setEntity(entity);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}