package org.jeesl.api.facade.module;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityWithCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaffPool;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSecurityFacade <L extends UtilsLang,
										D extends UtilsDescription,
										C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	extends UtilsFacade
{	
	
	R load(Class<R> cRole, R role);
	V load(Class<V> cView, V view);
	U load(Class<U> cUsecase, U usecase);
//	UtilsSecurityWithViews<L,D,C,R,V,U,A,AT,USER> load(Class<UtilsSecurityWithViews<L,D,C,R,V,U,A,AT,USER>> cRole, UtilsSecurityWithViews<L,D,C,R,V,U,A,AT,USER> sww);
	
	List<V> allViewsForUser(Class<USER> cUser, USER user);
	
	List<R> allRolesForUser(Class<USER> cUser, USER user);
	List<R> rolesForView(Class<V> cView, V view);
	List<R> rolesForView(Class<V> cView, Class<USER> cUser, V view, USER user);
	List<R> rolesForAction(Class<A> cAction, A action);
	List<R> rolesForAction(Class<A> cAction, Class<USER> cUser, A action, USER user);
	
	List<A> allActionsForUser(Class<USER> clUser, USER user);
	List<A> allActions(Class<R> cRole, List<R> roles);
	
	void grantRole(Class<USER> clUser, Class<R> clRole, USER user, R role, boolean grant);
	boolean hasRole(Class<USER> clUser, Class<R> clRole, USER user, R role);
		
	<WC extends UtilsSecurityWithCategory<L,D,C,R,V,U,A,AT,USER>> List<WC> allForCategory(Class<WC> clWc, Class<C> clC, String catCode) throws UtilsNotFoundException;
	
	<S extends UtilsStaffPool<L,D,C,R,V,U,A,AT,P,E,USER>, P extends EjbWithId, E extends EjbWithId> List<S> fStaffPool(Class<S> clStaff, P pool);
	
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> S fStaff(Class<S> cStaff, USER user, R role, D1 domain) throws UtilsNotFoundException;
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffU(Class<S> cStaff, USER user);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffR(Class<S> cStaff, R role);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffD(Class<S> cStaff, D1 domain);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffD(Class<S> cStaff, List<D1> domains);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffUR(Class<S> cStaff, USER user, R role);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffUD(Class<S> cStaff, USER user, D1 domain);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffRD(Class<S> cStaff, R role, D1 domain);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffRD(Class<S> cStaff, R role, List<D1> domains);
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<S> fStaffURD(Class<S> cStaff, USER user, R role, List<D1> domains);
	
	<S extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> List<D1> fDomains(Class<V> cView, Class<S> cStaff, USER user, V view);
}