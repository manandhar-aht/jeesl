package org.jeesl.interfaces.model.util.date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithTimeline extends EjbWithId,EjbWithDateRange
{	
	public boolean isAllDay();
	public void setAllDay(boolean allDay);
}