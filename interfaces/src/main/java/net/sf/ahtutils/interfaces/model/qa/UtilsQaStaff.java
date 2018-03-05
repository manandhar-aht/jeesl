package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslStaff;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsQaStaff<
					R extends JeeslSecurityRole<?,?,?,?,?,?,USER>,
					USER extends JeeslUser<R>,
					GROUP extends UtilsQaGroup<?,QA,?>,
					QA extends UtilsQualityAssurarance<?,?,QASH>,
					QASH extends UtilsQaStakeholder<?,?,?,R,?,?,?,?,USER,?,GROUP,QA,?,?,?,?,?,?,QASH,?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbPersistable,EjbWithId,JeeslStaff<R,USER,QA,QA>
{
	QASH getStakeholder();
	void setStakeholder(QASH stakeholder);
	
	String getDepartment();
	void setDepartment(String department);

	String getResponsibilities();
	void setResponsibilities(String responsibilities);
	
	Boolean getReportingRelevant();
	void setReportingRelevant(Boolean reportingRelevant);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}