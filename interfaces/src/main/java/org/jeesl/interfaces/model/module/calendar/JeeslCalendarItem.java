package org.jeesl.interfaces.model.module.calendar;

import java.io.Serializable;
import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslCalendarItem <L extends UtilsLang,
									D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>
									>
		extends  Serializable,EjbWithId,
					EjbSaveable
{
	public enum Attributes {calendar,startDate,endDate}
	
	IT getType();
	void setType(IT type);
	
	Date getStartDate();
	void setStartDate(Date startDate);

	ZONE getStartZone();
	void setStartZone(ZONE startZone);
	
	ZONE getEndZone();
	void setEndZone(ZONE endZone);
	
	Date getEndDate();
	void setEndDate(Date endDate);
	
	boolean isAllDay();
	void setAllDay(boolean allDay);
}