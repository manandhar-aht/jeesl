package net.sf.ahtutils.model.interfaces.with;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;

public interface EjbWithUser <USER extends JeeslUser<?>> extends EjbWithId
{
	public USER getUser();
	public void setUser(USER user);
}
