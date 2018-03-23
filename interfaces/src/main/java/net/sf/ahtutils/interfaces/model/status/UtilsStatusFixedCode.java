package net.sf.ahtutils.interfaces.model.status;

import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

public interface UtilsStatusFixedCode extends EjbWithCode
{					
	public List<String> getFixedCodes();
}