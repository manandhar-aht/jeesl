package org.jeesl.interfaces.model.system.security.util;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslStaff<R extends JeeslSecurityRole<?,?,?,?,?,?,USER>,
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