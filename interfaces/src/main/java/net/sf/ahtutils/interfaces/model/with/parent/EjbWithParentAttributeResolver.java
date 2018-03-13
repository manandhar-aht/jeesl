package net.sf.ahtutils.interfaces.model.with.parent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithParentAttributeResolver extends EjbWithId
{

	/**
	 * Define the parent attribute
	 * @return the property name of the parent (e.g. district, province)
	 */
	public String resolveParentAttribute();
}