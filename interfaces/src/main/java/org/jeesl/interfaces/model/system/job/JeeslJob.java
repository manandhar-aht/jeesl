package org.jeesl.interfaces.model.system.job;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslJob<L extends UtilsLang,D extends UtilsDescription,
							TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS>,
							FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
							STATUS extends UtilsStatus<STATUS,L,D>
							>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	public static enum Attributes{template,status,recordCreation,recordStart};
	public static enum Status{queue,working,completed,failed};
	public static enum Feedback{none,email,box}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	Date getRecordCreation();
	void setRecordCreation(Date recordCreation);
	
	Date getRecordStart();
	void setRecordStart(Date recordStart);
	
	Date getRecordComplete();
	void setRecordComplete(Date recordComplete);
	
	String getName();
	void setName(String name);
}