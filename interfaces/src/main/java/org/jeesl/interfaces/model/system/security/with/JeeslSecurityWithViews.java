package org.jeesl.interfaces.model.system.security.with;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSecurityWithViews <V extends JeeslSecurityView<?,?,?,?,V,?,?,?,?>>
										extends EjbWithId
{
	public List<V> getViews();
	public void setViews(List<V> views);
}