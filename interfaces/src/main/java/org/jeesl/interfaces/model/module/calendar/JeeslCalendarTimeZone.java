package org.jeesl.interfaces.model.module.calendar;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslCalendarTimeZone <L extends UtilsLang,
								D extends UtilsDescription,
								CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								CT extends UtilsStatus<CT,L,D>,
								ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
								IT extends UtilsStatus<IT,L,D>>
		extends EjbWithId,EjbSaveable,EjbWithCode,EjbWithLang<L>
{
	public static String tzUtc = "UTC";
	public static String tzBerlin = "Europe/Berlin";
}