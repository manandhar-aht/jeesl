package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoCmsMarkup <T extends UtilsStatus<T,?,?>> 
		extends Serializable,EjbSaveable
{	
	T getType();
	void setType(T type);
	
	String getLkey();
	public void setLkey(String lkey);
	
	String getContent();
	void setContent(String content);
}