package org.jeesl.factory.ejb.module.calendar;

import java.util.Date;
import java.util.TimeZone;

import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbTimeZoneFactory<L extends UtilsLang,
								D extends UtilsDescription,
								CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								CT extends UtilsStatus<CT,L,D>,
								ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								IT extends UtilsStatus<IT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTimeZoneFactory.class);
	
	public static String tzUtc = "UTC";
	
	private final Class<ZONE> cZone;
    
	public EjbTimeZoneFactory(final Class<ZONE> cZone)
	{  
        this.cZone = cZone;
	}
	    
	public ZONE build()
	{
		ZONE ejb = null;
		try
		{
			ejb = cZone.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public ITEM toUtc(ITEM item)
	{
		item.setStartDate(startToUtc(item));
		item.setEndDate(endToUtc(item));
		return item;
	}
	public Date startToUtc(ITEM item){return toUtc(item.getStartDate(),item.getStartZone().getCode());}
	public Date endToUtc(ITEM item){return toUtc(item.getEndDate(),item.getEndZone().getCode());}
	
	public static Date toUtc(Date d, String tzCode)
	{
		DateTime dt = new DateTime(d);
		DateTime srcDateTime = dt.toDateTime(DateTimeZone.forID(tzCode));
		DateTime utcDateTime = srcDateTime.withZone(DateTimeZone.forID(tzUtc));
		return utcDateTime.toLocalDateTime().toDateTime().toDate();
	}
	
	public static boolean supportedCode(String code)
	{
		for(String s : TimeZone.getAvailableIDs())
		{
			if(s.equals(code)){return true;}
		}
		return false;
	}
	
	
}