package org.jeesl.interfaces.model.system.security.with;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;

public interface JeeslSecurityWithActions <A extends JeeslSecurityAction<?,?,?,?,?,?>>
{
	public List<A> getActions();
	public void setActions(List<A> actions);
}