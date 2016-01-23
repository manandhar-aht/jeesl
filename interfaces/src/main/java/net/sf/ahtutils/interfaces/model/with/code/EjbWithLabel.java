package net.sf.ahtutils.interfaces.model.with.code;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithLabel extends EjbWithId
{	
	public String getLabel();
	public void setLabel(String label);
}