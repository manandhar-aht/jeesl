package org.jeesl.factory.json.util;

import java.util.Date;
import java.util.Locale;

import org.jeesl.model.json.util.Day;
import org.joda.time.DateTime;

public class JsonDayFactory
{
	public static final long serialVersionUID=1;
	private Locale locale;
	
	private final DateTime now;

	public JsonDayFactory(String localeCode)
	{
		if(localeCode.equals("de")){locale = Locale.GERMAN;}
		else if(localeCode.equals("fr")){locale = Locale.FRENCH;}
		else {locale = Locale.ENGLISH;}
		
		now = new DateTime(new Date());
	}
	
	public static Day build()
	{
		Day json = new Day();
		
		return json;
	}
	
	public Day build(DateTime dt)
	{
		Day json = build();
		json.setNr(dt.getDayOfMonth());
		json.setWeekend(dt.getDayOfWeek()>5);
		
		DateTime.Property pDoW = dt.dayOfWeek();
		json.setName(pDoW.getAsText(locale));
		
		json.setToday(now.getDayOfMonth()==dt.getDayOfMonth() && now.getMonthOfYear()==dt.getMonthOfYear() && now.getYear()==dt.getYear());
		
		return json;
	}
}