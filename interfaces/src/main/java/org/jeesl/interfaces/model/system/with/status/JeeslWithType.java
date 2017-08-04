package org.jeesl.interfaces.model.system.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithType<L extends UtilsLang, D extends UtilsDescription, TYPE extends UtilsStatus<TYPE,L,D>>
						extends EjbWithId
{
	public static String attributeType = "type";
	
	TYPE getType();
	void setType(TYPE category);
}