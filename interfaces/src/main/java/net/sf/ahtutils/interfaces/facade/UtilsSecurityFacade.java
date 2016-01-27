package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.security.UtilsStaffPool;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsSecurityFacade <L extends UtilsLang,
										D extends UtilsDescription,
										C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
	extends UtilsFacade
{	
	
	R load(Class<R> cRole, R role);
	V load(Class<V> cView, V view);
	List<V> allViewsForUser(Class<USER> cUser, USER user);
	
	List<R> allRolesForUser(Class<USER> cUser, USER user);
	List<R> rolesForView(Class<V> cView, V view);
	List<R> rolesForView(Class<V> cView, Class<USER> cUser, V view, USER user);
	List<R> rolesForAction(Class<A> cAction, A action);
	List<R> rolesForAction(Class<A> cAction, Class<USER> cUser, A action, USER user);
	
	List<A> allActionsForUser(Class<USER> clUser, USER user);
	
	void grantRole(Class<USER> clUser, Class<R> clRole, USER user, R role, boolean grant);
	boolean hasRole(Class<USER> clUser, Class<R> clRole, USER user, R role);
	
	<WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A,USER>> List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String catCode) throws UtilsNotFoundException;
	
	<S extends UtilsStaffPool<L,D,C,R,V,U,A,P,E,USER>, P extends EjbWithId, E extends EjbWithId> List<S> fStaffPool(Class<S> clStaff, P pool);
	
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> S fStaff(Class<S> clStaff, USER user, R role, DOMAIN domain) throws UtilsNotFoundException;
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<S> fStaffU(Class<S> clStaff, USER user);
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<S> fStaffR(Class<S> clStaff, R role);
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<S> fStaffD(Class<S> clStaff, DOMAIN domain);
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<S> fStaffUR(Class<S> clStaff, USER user, R role);
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<S> fStaffUD(Class<S> clStaff, USER user, DOMAIN domain);
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<S> fStaffRD(Class<S> clStaff, R role, DOMAIN domain);
	
	<S extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>, DOMAIN extends EjbWithId> List<DOMAIN> fDomains(Class<V> cView, Class<S> cStaff, USER user, V view);
}