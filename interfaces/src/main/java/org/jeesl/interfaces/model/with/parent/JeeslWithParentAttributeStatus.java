package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.with.status.JeeslWithStatus;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;

public interface JeeslWithParentAttributeStatus <STATUS extends UtilsStatus<STATUS,?,?>> 
								extends EjbWithParentAttributeResolver,JeeslWithStatus<STATUS>
{


}