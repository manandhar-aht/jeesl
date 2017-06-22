package net.sf.ahtutils.model.interfaces.with;

import java.io.Serializable;

public interface EjbWithId extends Serializable
{
	public static String attribute = "id";
	
	public long getId();
	public void setId(long id);
}
