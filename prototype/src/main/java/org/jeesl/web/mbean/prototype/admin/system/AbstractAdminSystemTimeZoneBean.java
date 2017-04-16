package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.factory.ejb.module.calendar.EjbTimeZoneFactory;
import org.jeesl.factory.factory.CalendarFactoryFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.jeesl.util.comparator.ejb.module.TimeZoneComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractAdminSystemTimeZoneBean <L extends UtilsLang,
											D extends UtilsDescription,
											CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
											ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
											CT extends UtilsStatus<CT,L,D>,
											ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
											IT extends UtilsStatus<IT,L,D>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSystemTimeZoneBean.class);
	
	private JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar;
	
	private Class<ZONE> cZone;
	
	private List<ZONE> zones; public List<ZONE> getZones() {return zones;}
	
	private ZONE zone; public ZONE getZone() {return zone;} public void setZone(ZONE zone) {this.zone = zone;}
	
	private Comparator<ZONE> comparatorTimeZone;
	private EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> efZone;
	
	public void initSuper(JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar, FacesMessageBean bMessage, String[] localeCodes, final Class<L> cL, final Class<D> cD, final Class<ZONE> cZone, final Class<IT> cItemType)
	{
		super.initAdmin(localeCodes, cL, cD, bMessage);
		this.fCalendar=fCalendar;
		this.cZone=cZone;

		CalendarFactoryFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> ffCalendar = CalendarFactoryFactory.factory(cL,cD,cZone,cItemType);
		efZone = ffCalendar.zone();
		
		comparatorTimeZone = (new TimeZoneComparator<L,D,CALENDAR,ZONE,CT,ITEM,IT>()).factory(TimeZoneComparator.Type.offset);
		reload();	
	}
	
	
	private void reload()
	{
		zones  = fCalendar.all(cZone);
		Collections.sort(zones,comparatorTimeZone);
	}
	
	public void selectZone() throws UtilsNotFoundException
	{
		zone = fCalendar.find(cZone,zone);
	}
	
	public void addZone() throws UtilsNotFoundException
	{
		zone = efZone.build();
		zone.setName(efLang.createEmpty(langs));
	}
	
	public void saveZone() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(EjbTimeZoneFactory.supportedCode(zone.getCode()))
		{
			zone = fCalendar.save(zone);
			updatePerformed();
			bMessage.growlSuccessSaved();
		}
		else
		{
			bMessage.errorText("TS not supported");
		}

		reload();
	}
	
	protected void updatePerformed(){}
}