package org.jeesl.interfaces.model.system.io.mail.core;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoMail<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS,RETENTION>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								RETENTION extends UtilsStatus<RETENTION,L,D>
								>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	

	public static enum Retention{fully,partially,toDelete};
	public static enum Attributes{category,status,recordCreation,recordSpool};
	
	Long getVersionLock();
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	RETENTION getRetention();
	void setRetention(RETENTION retention);
	
	Date getRecordCreation();
	void setRecordCreation(Date recordCreation);
	
	Date getRecordSpool();
	void setRecordSpool(Date recordSpool);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	String getRecipient();
	void setRecipient(String recipient);
	
	int getCounter();
	void setCounter(int counter);
	
	Integer getRetentionDays();
	void setRetentionDays(Integer retentionDays);
	
	String getXml();
	void setXml(String xml);
}