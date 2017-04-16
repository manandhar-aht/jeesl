package org.jeesl.factory.factory;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.controller.handler.CalendarItemHandler;
import org.jeesl.factory.ejb.module.calendar.EjbTimeZoneFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class CalendarFactoryFactory<L extends UtilsLang,
									D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(CalendarFactoryFactory.class);
	
//	private final Class<L> cL;
//	private final Class<D> cD;
	private final Class<ZONE> cZone;
	private final Class<IT> cItemType;

    
	private CalendarFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<ZONE> cZone, final Class<IT> cItemType)
	{       
//		this.cL = cL;
//       this.cD = cD;
        this.cZone = cZone;
        this.cItemType = cItemType;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
					ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
					CT extends UtilsStatus<CT,L,D>,
					ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
					IT extends UtilsStatus<IT,L,D>>
		CalendarFactoryFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> factory(final Class<L> cL,final Class<D> cD,final Class<ZONE> cZone, final Class<IT> cItemType)
	{
		return new CalendarFactoryFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT>(cL,cD,cZone,cItemType);
	}
	
	public EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> zone()
	{
		return new EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT>(cZone);
	}
	
	public CalendarItemHandler<L,D,CALENDAR,ZONE,CT,ITEM,IT> itemHandler(final JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar)
	{
		EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> efZone = zone();
		return new CalendarItemHandler<L,D,CALENDAR,ZONE,CT,ITEM,IT>(fCalendar,cZone,cItemType,efZone);
	}
}