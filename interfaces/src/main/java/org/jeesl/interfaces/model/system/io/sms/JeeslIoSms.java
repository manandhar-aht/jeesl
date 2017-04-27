package org.jeesl.interfaces.model.system.io.sms;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSms<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								SMS extends JeeslIoSms<L,D,CATEGORY,SMS,STATUS>,
								STATUS extends UtilsStatus<STATUS,L,D>
								>
		extends EjbWithId,EjbWithRefId,EjbSaveable,EjbRemoveable
{	
	
	public static enum Status{queue,spooling,sent,failed};
	public static enum Attributes{category,status,recordCreation,recordSpool};
	
	Long getVersionLock();
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	Date getRecordCreation();
	void setRecordCreation(Date recordCreation);
	
	Date getRecordSpool();
	void setRecordSpool(Date recordSpool);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	int getCounter();
	void setCounter(int counter);
	
	String getRecipient();
	void setRecipient(String recipient);
		
	String getTxt();
	void setTxt(String txt);
}