package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsQaResult<STAFF extends UtilsQaStaff<?,?,?,?,?>,
				QAT extends UtilsQaTest<?,?,?,?,?,?,?,?,?,STAFF,?,?,?,?,?,QAT,?,?,?,?,?,?,?,QARS,?>,
				QARS extends UtilsStatus<QARS,?,?>>
			extends Serializable,EjbSaveable,EjbWithRecord,EjbWithId
{
	QAT getTest();
	void setTest(QAT test);
	
	STAFF getStaff();
	void setStaff(STAFF staff);
	
	QARS getStatus();
	void setStatus(QARS status);
	
	String getActualResult();
	void setActualResult(String actualResult);
	
	String getComment();
	void setComment(String comment);
	
	Date getRecord();
	void setRecord(Date record);
}