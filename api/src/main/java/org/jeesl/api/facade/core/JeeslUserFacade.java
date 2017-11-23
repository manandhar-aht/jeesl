package org.jeesl.api.facade.core;

import java.util.List;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface JeeslUserFacade <USER extends JeeslUser<?>> extends UtilsFacade
{	
	USER load(USER user);
	List<USER> likeNameFirstLast(String query);
}