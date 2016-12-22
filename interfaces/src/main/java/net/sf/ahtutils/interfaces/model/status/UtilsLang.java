package net.sf.ahtutils.interfaces.model.status;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsLang extends EjbWithId,EjbRemoveable,EjbSaveable
{	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
}