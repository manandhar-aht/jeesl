package net.sf.ahtutils.interfaces.model.status;

import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsDescription extends EjbRemoveable,EjbWithId
{
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
	
	public Boolean getStyled();
	public void setStyled(Boolean styled);
}