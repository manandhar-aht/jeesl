package org.jeesl.factory.builder.module;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.controller.handler.CalendarItemHandler;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.calendar.EjbTimeZoneFactory;
import org.jeesl.factory.txt.module.calendar.TxtCalendarItemFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class CalendarFactoryBuilder<L extends UtilsLang,
									D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(CalendarFactoryBuilder.class);
	
	private final Class<ZONE> cZone; public Class<ZONE> getClassZone()	{return cZone;}
	private final Class<IT> cItemType;

	public CalendarFactoryBuilder(final Class<L> cL,final Class<D> cD,final Class<ZONE> cZone, final Class<IT> cItemType)
	{       
		super(cL,cD);
        this.cZone = cZone;
        this.cItemType = cItemType;
	}
	
	public EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> zone(){return new EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT>(cZone);}
	
	public TxtCalendarItemFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> txtItem() {return new TxtCalendarItemFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT>();}
	
	public CalendarItemHandler<L,D,CALENDAR,ZONE,CT,ITEM,IT> itemHandler(final JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar){return new CalendarItemHandler<L,D,CALENDAR,ZONE,CT,ITEM,IT>(fCalendar,cZone,cItemType,zone(),txtItem());}
}