package net.sf.ahtutils.model.interfaces.with;

import java.io.Serializable;

public interface EjbWithId extends Serializable
{
	String attribute = "id";
	
	long getId();
	void setId(long id);
}
