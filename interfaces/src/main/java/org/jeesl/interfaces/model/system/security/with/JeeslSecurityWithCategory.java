package org.jeesl.interfaces.model.system.security.with;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;

import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSecurityWithCategory<C extends JeeslSecurityCategory<?,?>>
				extends EjbWithId,EjbWithPositionVisibleParent,EjbWithPositionParent
{
	public C getCategory();
	public void setCategory(C category);
}