package org.jeesl.interfaces.model.system.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithType<TYPE extends UtilsStatus<TYPE,?,?>>extends EjbWithId
{
	public static String attributeType = "type";
	
	TYPE getType();
	void setType(TYPE type);
}