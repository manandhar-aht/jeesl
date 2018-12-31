package org.jeesl.interfaces.model.with;

import net.sf.ahtutils.interfaces.model.date.EjbWithValidFrom;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithValidFromAndParent extends EjbWithId,EjbWithValidFrom,EjbWithParentAttributeResolver
{
	
}