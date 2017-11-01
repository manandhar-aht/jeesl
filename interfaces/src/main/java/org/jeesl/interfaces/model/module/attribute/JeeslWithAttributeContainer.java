package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithAttributeContainer <CONTAINER extends JeeslAttributeContainer<?,?>>
		extends EjbWithId
{
	CONTAINER getAttributeContainer();
	void setAttributeContainer(CONTAINER attributeContainer);
}