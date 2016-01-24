package net.sf.ahtutils.factory.json;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.AhtUtilsBootstrap;
import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.date.EntityWithDate;
import net.sf.ahtutils.interfaces.model.date.EntityWithDay;
import net.sf.ahtutils.interfaces.model.date.EntityWithMonth;
import net.sf.ahtutils.interfaces.model.date.EntityWithQuarter;
import net.sf.ahtutils.interfaces.model.date.EntityWithWeek;
import net.sf.ahtutils.interfaces.model.date.EntityWithYearMonthQuarter;

public class JsonDateFactory
{
	final static Logger logger = LoggerFactory.getLogger(AhtUtilsBootstrap.class);
	
	@Deprecated //use build method
	public static void apply(EntityWithYearMonthQuarter json, Date date) 
	{
		DateTime dt = new DateTime(date);
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int quarter = (month / 3) + 1;
		
		json.setYear(dt.getYear());
		json.setQuarter("Q" + quarter);
		json.setMonth(dt.getMonthOfYear());
	}
	
	public static void build(EntityWithDate json, Date date) 
	{
		DateTime dt = new DateTime(date);
		
		if((json instanceof EjbWithYear)){((EjbWithYear) json).setYear(dt.getYear());}
		if((json instanceof EntityWithMonth)){((EntityWithMonth) json).setMonth(dt.getMonthOfYear());}
		if((json instanceof EntityWithQuarter))
		{
			int quarter = (dt.getMonthOfYear() / 3) + 1;
			((EntityWithQuarter) json).setQuarter("Q"+quarter);
		}
		if((json instanceof EntityWithWeek)){((EntityWithWeek) json).setWeek(dt.getWeekOfWeekyear());}
		if((json instanceof EntityWithDay)){((EntityWithDay) json).setDay(dt.getDayOfMonth());}
		
	}
}