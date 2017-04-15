package org.jeesl.util.comparator.ejb.module;

import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TimeZoneComparator<L extends UtilsLang,
									D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(TimeZoneComparator.class);

    public enum Type {offset};
    
    public Comparator<ZONE> factory(Type type)
    {
        Comparator<ZONE> c = null;
        TimeZoneComparator<L,D,CALENDAR,ZONE,CT,ITEM,IT> factory = new TimeZoneComparator<L,D,CALENDAR,ZONE,CT,ITEM,IT>();
        switch (type)
        {
            case offset: c = factory.new OffsetComparator();break;
        }

        return c;
    }

    private class OffsetComparator implements Comparator<ZONE>
    {
        public int compare(ZONE a, ZONE b)
        {
        	Date d = new Date();
        	TimeZone tzA = TimeZone.getTimeZone(a.getCode());
        	TimeZone tzB = TimeZone.getTimeZone(b.getCode());
        	
        	CompareToBuilder ctb = new CompareToBuilder();
        	ctb.append(tzA.getOffset(d.getTime()), tzB.getOffset(d.getTime()));
        	ctb.append(a.getCode(),b.getCode());
        	return ctb.toComparison();
        }
    }
}