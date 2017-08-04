package org.jeesl.interfaces.model.system.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithStatus<L extends UtilsLang, D extends UtilsDescription, STATUS extends UtilsStatus<STATUS,L,D>>
						extends EjbWithId
{
	public static String attributeStatus = "status";
	
	STATUS getStatus();
	void setStatus(STATUS category);
}