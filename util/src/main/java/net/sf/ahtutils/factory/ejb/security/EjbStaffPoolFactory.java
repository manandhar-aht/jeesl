package  net.sf.ahtutils.factory.ejb.security;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsStaffPool;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EjbStaffPoolFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										 S extends UtilsStaffPool<L,D,C,R,V,U,A,P,E,USER>,
										 P extends EjbWithId,
										 E extends EjbWithId,
										 USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStaffPoolFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<C> clCategory;
    final Class<R> clRole;
    final Class<V> clView;
    final Class<U> clUsecase;
    final Class<A> clAction;
    final Class<S> clStaff;
    final Class<P> clPool;
    final Class<E> clEntity;
    final Class<USER> clUser;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	 			   S extends UtilsStaffPool<L,D,C,R,V,U,A,P,E,USER>,
	 			   P extends EjbWithId,
	 			   E extends EjbWithId,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
    	EjbStaffPoolFactory<L,D,C,R,V,U,A,S,P,E,USER> factory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<S> clStaff, final Class<P> clPool, final Class<E> clEntity,final Class<USER> clUser)
    {
        return new EjbStaffPoolFactory<L,D,C,R,V,U,A,S,P,E,USER>(clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clStaff,clPool,clEntity,clUser);
    }
    
    public EjbStaffPoolFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole,final Class<V> clView,final Class<U> clUsecase,final Class<A> clAction,final Class<S> clStaff, final Class<P> clPool,final Class<E> clEntity,final Class<USER> clUser)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clCategory = clCategory;
        this.clRole = clRole;
        this.clView = clView;
        this.clUsecase = clUsecase;
        this.clAction = clAction;
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