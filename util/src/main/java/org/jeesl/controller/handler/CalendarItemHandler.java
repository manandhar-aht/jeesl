package org.jeesl.controller.handler;

import java.io.Serializable;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.factory.ejb.module.calendar.EjbTimeZoneFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class CalendarItemHandler <L extends UtilsLang,
									D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>
									>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(CalendarItemHandler.class);

	private final JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar;
	private final EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> efZone;

	private final Class<ZONE> cZone;
	private final Class<IT> cItemType;
	
	private ITEM item; public ITEM getItem() {return item;} public void setItem(ITEM item) {this.item = item;}

	public CalendarItemHandler(final JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar, final Class<ZONE> cZone, final Class<IT> cItemType, EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> efZone)
	{
		this.fCalendar=fCalendar;
		
		this.cZone=cZone;
		this.cItemType=cItemType;
		
		this.efZone=efZone;
	}
	
	public void saveItem() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Saving ...");
		item.setStartZone(fCalendar.find(cZone,item.getStartZone()));
		item.setEndZone(fCalendar.find(cZone,item.getEndZone()));
		item = efZone.toUtc(item);
		
		item.setType(fCalendar.find(cItemType,item.getType()));
		item = fCalendar.save(item);
	}
}