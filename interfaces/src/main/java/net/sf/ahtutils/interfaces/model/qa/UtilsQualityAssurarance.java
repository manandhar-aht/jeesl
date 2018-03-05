package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsQualityAssurarance<
					STAFF extends UtilsQaStaff<?,?,?,?,QASH>,
					QAC extends UtilsQaCategory<?,?>,
					QASH extends UtilsQaStakeholder<?,?,?,?,?,?,?,?,?,STAFF,?,?,?,?,QAC,?,?,?,QASH,?,?,?,?,?,?>>
			extends Serializable,EjbWithId
{
	List<QAC> getCategories();
	void setCategories(List<QAC> categories);
	
	List<STAFF> getStaff();
	void setStaff(List<STAFF> staff);
	
	List<QASH> getStakeholders();
	void setStakeholders(List<QASH> stakeholders);
}