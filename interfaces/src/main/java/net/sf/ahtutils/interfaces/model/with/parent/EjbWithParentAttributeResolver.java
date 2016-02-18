package net.sf.ahtutils.interfaces.model.with.parent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithParentAttributeResolver extends EjbWithId
{
	public String resolveParentAttribute();
}