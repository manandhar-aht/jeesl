package org.jeesl.factory.ejb.module.calendar;

import java.util.TimeZone;

import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
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
	
	public static boolean supportedCode(String code)
	{
		for(String s : TimeZone.getAvailableIDs())
		{
			if(s.equals(code)){return true;}
		}
		return false;
	}
}