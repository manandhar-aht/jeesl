package net.sf.ahtutils.interfaces.model.system.mail;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsMailTracker<S extends UtilsStatus<S,L,D>,
									L extends UtilsLang,
									U extends EjbWithId,
									D extends UtilsDescription>
						extends EjbWithId
{
	long getRefId();
	void setRefId(long refId);
	
	S getType();
	void setType(S type);
	
	Date getRecordCreated();
	void setRecordCreated(Date recordGenerated);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	int getRetryCounter();
	void setRetryCounter(int retryCounter);
	
	U getUser();
	void setUser(U user);
}