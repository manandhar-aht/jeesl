package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.crud.EjbCrudWithParent;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.date.EjbWithDateRange;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaScheduleSlot<GROUP extends UtilsQaGroup<?,?,?>,QASD extends UtilsQaSchedule<?,?>>
			extends Serializable,EjbCrudWithParent,EjbPersistable,EjbRemoveable,EjbWithId,EjbWithDateRange,EjbWithName
{
	QASD getSchedule();
	void setSchedule(QASD schedule);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}