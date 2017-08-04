package org.jeesl.interfaces.model.system.with.code;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithCode extends EjbWithId
{	
	public String getCode();
	public void setCode(String code);
}