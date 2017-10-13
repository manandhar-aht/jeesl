package org.jeesl.interfaces.model.system.revision;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRevision extends EjbWithId
{					
	long getId();
	void setId(long id);

	Date getAuditRecord();
	void setAuditRecord(Date auditRecord);

	Long getUserId();
	void setUserId(Long userId);
	
	String getIpAddress();
	void setIpAddress(String ipAddress);
}