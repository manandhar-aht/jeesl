package org.jeesl.interfaces.model.system.security.util;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslStaff<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends JeeslSecurityCategory<L,D>,
								   R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
								   V extends JeeslSecurityView<L,D,C,R,U,A>,
								   U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
								   A extends JeeslSecurityAction<L,D,R,V,U,AT>,
								   AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								   USER extends JeeslUser<R>,
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
	
	D2 getDomain2();
	void setDomain2(D2 domain2);
}