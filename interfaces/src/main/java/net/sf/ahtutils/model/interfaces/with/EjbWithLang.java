package net.sf.ahtutils.model.interfaces.with;

import java.util.Map;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface EjbWithLang<L extends UtilsLang>
{	
	public static String attributeName = "name";
	
	public Map<String,L> getName();
	public void setName(Map<String,L> name);
}