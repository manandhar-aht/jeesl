package org.jeesl.interfaces.model.system.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithStatus<STATUS extends UtilsStatus<STATUS,?,?>>
						extends EjbWithId
{
	public static String attributeStatus = "status";
	
	STATUS getStatus();
	void setStatus(STATUS status);
}