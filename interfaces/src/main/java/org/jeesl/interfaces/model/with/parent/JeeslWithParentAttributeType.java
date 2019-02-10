package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.system.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;

public interface JeeslWithParentAttributeType <TYPE extends UtilsStatus<TYPE,?,?>> 
								extends EjbWithParentAttributeResolver,JeeslWithType<TYPE>
{


}