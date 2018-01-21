package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslJob<TEMPLATE extends JeeslJobTemplate<?,?,?,?,?>,
							PRIORITY extends UtilsStatus<PRIORITY,?,?>,
							FEEDBACK extends JeeslJobFeedback<?,?,USER>,
							STATUS extends UtilsStatus<STATUS,?,?>,
							USER extends EjbWithEmail
							>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithNonUniqueCode
{	
	public static enum Attributes{template,status,priority,recordCreation,recordStart,code};
	
	public static enum Type{reportXml,reportXlsx}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	PRIORITY getPriority();
	void setPriority(PRIORITY priority);
	
	Date getRecordCreation();
	void setRecordCreation(Date recordCreation);
	
	Date getRecordStart();
	void setRecordStart(Date recordStart);
	
	Date getRecordComplete();
	void setRecordComplete(Date recordComplete);
	
	String getName();
	void setName(String name);
	
	USER getUser();
	void setUser(USER user);
	
	Integer getAttempts();
	void setAttempts(Integer attempts);
	
	List<FEEDBACK> getFeedbacks();
	void setFeedbacks(List<FEEDBACK> feedbacks);
}