package org.jeesl.interfaces.model.system.with.code;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithNrString extends EjbWithId
{	
	public static String attributeNr = "nr";
	
	public String getNr();
	public void setNr(String nr);
}