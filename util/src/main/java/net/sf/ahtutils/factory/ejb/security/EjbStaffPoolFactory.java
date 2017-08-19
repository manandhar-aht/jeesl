package  net.sf.ahtutils.factory.ejb.security;

import org.jeesl.factory.ejb.system.security.AbstractEjbSecurityFactory;
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
import net.sf.ahtutils.interfaces.model.util.UtilsStaffPool;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;


public class EjbStaffPoolFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
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
	 			   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			  AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
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