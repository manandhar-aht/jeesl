package org.jeesl.interfaces.model.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithLevel<LEVEL extends UtilsStatus<LEVEL,?,?>>
						extends EjbWithId
{
	public static String attributeLevel = "level";
	
	LEVEL getLevel();
	void setLevel(LEVEL level);
}