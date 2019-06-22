package org.jeesl.interfaces.bean.system;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;

public interface JeeslSessionRegistryBean<USER extends JeeslUser<?>> extends Serializable
{
	List<USER> getUsers();
	
	public int activeSessions();
	public int authenticatedSessions();
}