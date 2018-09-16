package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;

public interface JeeslSecurityDocument<V extends JeeslSecurityView<?,?,?,?,?,?>,
											S extends JeeslIoCmsSection<?,S>>
			extends Serializable,EjbSaveable,EjbWithPositionVisible
{	
	V getView();
	void setView(V view);
	
	S getSection();
	void setSection(S section);
}