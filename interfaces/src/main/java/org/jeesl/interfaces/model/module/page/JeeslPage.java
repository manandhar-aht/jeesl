package org.jeesl.interfaces.model.module.page;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;

public interface JeeslPage<V extends JeeslSecurityView<?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbWithPosition
{
	V getView();
	void setView(V view);
}