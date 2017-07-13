package org.jeesl.factory.txt.module.calendar;

import java.util.Date;

import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtCalendarItemFactory<L extends UtilsLang, D extends UtilsDescription,
								CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								CT extends UtilsStatus<CT,L,D>,
								ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								IT extends UtilsStatus<IT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtCalendarItemFactory.class);
    
	public TxtCalendarItemFactory()
	{  

	}
	    
	public String debug(ITEM item)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("[").append(item.getId()).append("]");
		sb.append(" ").append(debug(item.getStartDate(),item.getEndDate()));
		
		return sb.toString();
	}

	public static String debug(Date start, Date end)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(dateTime(start));
		sb.append(" -> ").append(dateTime(end));
		return sb.toString();
	}
	
	private static String dateTime(Date date)
	{
		StringBuffer sb = new StringBuffer();
		
		DateTime dt = new DateTime(date);
		sb.append(dt.getHourOfDay());
		
		return sb.toString();
	}
}