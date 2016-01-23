package net.sf.ahtutils.factory.json;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import net.sf.ahtutils.interfaces.model.date.EntityWithYearMonthQuarter;

public class JsonDateFactory
{
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
}