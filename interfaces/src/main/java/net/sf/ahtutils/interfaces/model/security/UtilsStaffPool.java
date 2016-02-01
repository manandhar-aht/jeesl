package net.sf.ahtutils.interfaces.model.security;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsStaffPool<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								   P extends EjbWithId,
								   E extends EjbWithId,
								   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
			extends EjbWithId
{
	public R getRole();
	public void setRole(R role);
	
	public P getPool();
	public void setPool(P pool);
	
	public E getEntity();
	public void setEntity(E entity);
}