package org.jeesl.interfaces.model.util.date;

import java.util.Date;

public interface EjbWithValidFrom
{
	public static enum Attributes{validFrom}
	
	public Date getValidFrom();
	public void setValidFrom(Date validFrom);
}