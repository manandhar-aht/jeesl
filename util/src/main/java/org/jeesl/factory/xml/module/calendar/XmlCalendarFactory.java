package org.jeesl.factory.xml.module.calendar;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.jeesl.model.xml.module.calendar.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlCalendarFactory <L extends UtilsLang, D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>
									>
{
	final static Logger logger = LoggerFactory.getLogger(XmlCalendarFactory.class);
	
	@SuppressWarnings("unused")
	private XmlTypeFactory<CT,L,D> xfType;
	
	public XmlCalendarFactory(String localeCode, Calendar q)
	{
		if(q.isSetType()){xfType = new XmlTypeFactory<CT,L,D>(localeCode,q.getType());}
	}
	
	public Calendar build(CALENDAR calendar)
	{
		Calendar xml = build();		
//		if(q.isSetType()){xml.setType(xfType.build(calendar.g));
		
		return xml;
	}
	
	public static Calendar build()
	{
		Calendar xml = new Calendar();		
		return xml;
	}
}