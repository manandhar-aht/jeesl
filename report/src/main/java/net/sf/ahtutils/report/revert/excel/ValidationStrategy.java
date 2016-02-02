package net.sf.ahtutils.report.revert.excel;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface ValidationStrategy {
	
	public Boolean 					 validate(Object object, String parameterClass, String property);
	public void   					 setFacade(UtilsFacade facade);
	public void   					 setTempPropertyStore(Hashtable<String, Object> tempProperties);
	public Hashtable<String, Object> getTempPropertyStore();
}
