package org.jeesl.interfaces.model.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithContext<CTX extends UtilsStatus<CTX,?,?>> extends EjbWithId
{
	public static String attributeContext = "context";
	
	CTX getContext();
	void setContext(CTX context);
}