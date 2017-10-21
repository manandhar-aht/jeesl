package org.jeesl.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithRendered extends EjbWithId
{
	public static String attributeRendered = "rendered";
	
	public boolean isRendered();
	public void setRendered(boolean rendered);
}