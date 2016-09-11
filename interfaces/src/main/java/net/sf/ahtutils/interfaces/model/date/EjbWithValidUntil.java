package net.sf.ahtutils.interfaces.model.date;

import java.util.Date;

public interface EjbWithValidUntil
{
	public static enum Attributes{validUntil}
	
	public Date getValidUntil();
	public void setValidUntil(Date validUntil);
}
