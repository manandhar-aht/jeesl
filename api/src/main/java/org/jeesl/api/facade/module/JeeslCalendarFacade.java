package org.jeesl.api.facade.module;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslCalendarFacade <L extends UtilsLang,
										D extends UtilsDescription,
										CALENDAR extends JeeslCalendar<L,D,CALENDAR,CT,ITEM,IT>,
										CT extends UtilsStatus<CT,L,D>,
										ITEM extends JeeslCalendarItem<L,D,CALENDAR,CT,ITEM,IT>,
										IT extends UtilsStatus<IT,L,D>
										>
			extends UtilsFacade
{	
	List<ITEM> fCalendarItems(CALENDAR calendar, Date from, Date to);
	List<ITEM> fCalendarItems(List<CALENDAR> calendars, Date from, Date to);
}