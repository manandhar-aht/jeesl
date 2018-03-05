package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbPositionCrudWithParent;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsQaGroup<STAFF extends UtilsQaStaff<?,?,?,QA,?>,
							QA extends UtilsQualityAssurarance<STAFF,?,?>,
							QASS extends UtilsQaScheduleSlot<?,?>>
			extends Serializable,EjbPositionCrudWithParent,EjbPersistable,EjbRemoveable,EjbWithId,EjbWithPosition
{
	QA getQa();
	void setQa(QA qa);
	
    String getName();
	void setName(String name);
	
	String getDescription();
	void setDescription(String description);
	
	List<STAFF> getStaffs();
	void setStaffs(List<STAFF> staffs);
	
	List<QASS> getSlots();
	void setSlots(List<QASS> slots);
}