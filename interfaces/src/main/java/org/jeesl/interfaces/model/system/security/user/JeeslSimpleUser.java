package org.jeesl.interfaces.model.system.security.user;

import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public interface JeeslSimpleUser extends EjbWithEmail
{
	String getFirstName();
	void setFirstName(String firstName);
	
	String getLastName();
	void setLastName(String lastName);
}