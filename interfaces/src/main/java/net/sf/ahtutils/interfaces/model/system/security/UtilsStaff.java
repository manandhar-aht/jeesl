package net.sf.ahtutils.interfaces.model.system.security;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsStaff<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
								   D1 extends EjbWithId, D2 extends EjbWithId>
			extends EjbWithId,EjbSaveable,EjbRemoveable
{
	public enum Attributes {role,user,domain};
	
	R getRole();
	void setRole(R role);
	
	USER getUser();
	void setUser(USER user);
	
	D1 getDomain();
	void setDomain(D1 domain);
}