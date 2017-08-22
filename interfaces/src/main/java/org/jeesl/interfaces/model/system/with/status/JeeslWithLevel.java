package org.jeesl.interfaces.model.system.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithLevel<L extends UtilsLang, D extends UtilsDescription, LEVEL extends UtilsStatus<LEVEL,L,D>>
						extends EjbWithId
{
	public static String attributeLevel = "level";
	
	LEVEL getLevel();
	void setLevel(LEVEL level);
}