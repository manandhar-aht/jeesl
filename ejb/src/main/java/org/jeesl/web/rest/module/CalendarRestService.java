package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.api.rest.module.calendar.JeeslCalendarRestExport;
import org.jeesl.api.rest.module.calendar.JeeslCalendarRestImport;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class CalendarRestService <L extends UtilsLang,
								D extends UtilsDescription,
								CALENDAR extends JeeslCalendar<L,D,CALENDAR,CT,ITEM,IT>,
								CT extends UtilsStatus<CT,L,D>,
								ITEM extends JeeslCalendarItem<L,D,CALENDAR,CT,ITEM,IT>,
								IT extends UtilsStatus<IT,L,D>
								>
					extends AbstractJeeslRestService<L,D>
					implements JeeslCalendarRestExport,JeeslCalendarRestImport
{
	final static Logger logger = LoggerFactory.getLogger(CalendarRestService.class);
	
	private final JeeslCalendarFacade<L,D,CALENDAR,CT,ITEM,IT> fCalendar;
	
	private final Class<CT> cType;
	private final Class<IT> cItemType;
	
	private CalendarRestService(JeeslCalendarFacade<L,D,CALENDAR,CT,ITEM,IT> fCalendar,final Class<L> cL, final Class<D> cD, final Class<CT> cType, final Class<IT> cItemType)
	{
		super(fCalendar,cL,cD);
		this.fCalendar=fCalendar;
		
		this.cType=cType;
		this.cItemType=cItemType;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CALENDAR extends JeeslCalendar<L,D,CALENDAR,CT,ITEM,IT>,
					CT extends UtilsStatus<CT,L,D>,
					ITEM extends JeeslCalendarItem<L,D,CALENDAR,CT,ITEM,IT>,
					IT extends UtilsStatus<IT,L,D>>
			CalendarRestService<L,D,CALENDAR,CT,ITEM,IT>
			factory(JeeslCalendarFacade<L,D,CALENDAR,CT,ITEM,IT> fCalendar,final Class<L> cL, final Class<D> cD, final Class<CT> cType, final Class<IT> cItemType)
	{
		return new CalendarRestService<L,D,CALENDAR,CT,ITEM,IT>(fCalendar,cL,cD,cType,cItemType);
	}
	
	@Override public Container exportCalendarType() {return xfContainer.build(fCalendar.allOrderedPosition(cType));}
	@Override public Container exportCalendarItemType() {return xfContainer.build(fCalendar.allOrderedPosition(cItemType));}

	@Override public DataUpdate importCalendarType(Container container){return importStatus(cType,container,null);}
	@Override public DataUpdate importCalendarItemType(Container container){return importStatus(cType,container,null);}
}