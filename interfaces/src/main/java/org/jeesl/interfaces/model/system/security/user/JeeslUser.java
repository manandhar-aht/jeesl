package org.jeesl.interfaces.model.system.security.user;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslUser <R extends JeeslSecurityRole<?,?,?,?,?,?,?>
							//,REGISTRATION>,
//							REGISTRATION extends JeeslRegistrationStatus<L,D,USER,?>
>
		extends Serializable,EjbWithId,EjbPersistable,EjbSaveable,EjbRemoveable
{	
	String getFirstName();
	void setFirstName(String firstName);
	
	String getLastName();
	void setLastName(String lastName);
	
	Boolean getPermitLogin();
	void setPermitLogin(Boolean permitLogin);
	
	List<R> getRoles();
	void setRoles(List<R> roles);
}