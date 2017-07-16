package net.sf.ahtutils.model.interfaces.with;

import java.util.Date;

public interface EjbWithRecord extends EjbWithId
{
	public static String attributeRecord = "record";
	
	public Date getRecord();
	public void setRecord(Date record);
}
