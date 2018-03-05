package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsQaTestDiscussion<STAFF extends UtilsQaStaff<?,?,?,?,?>,
										QAT extends UtilsQaTest<?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbWithRecord,EjbWithId
{
	QAT getTest();
	void setTest(QAT test);
	
	STAFF getStaff();
	void setStaff(STAFF staff);
}