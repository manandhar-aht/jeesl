package org.jeesl.factory.json.util;

import java.util.Locale;

import org.jeesl.model.json.util.Day;
import org.joda.time.DateTime;

public class JsonDayFactory
{
	public static final long serialVersionUID=1;
	private Locale locale;
	
	public JsonDayFactory(String localeCode)
	{
		if(localeCode.equals("de")){locale = Locale.GERMAN;}
		else if(localeCode.equals("fr")){locale = Locale.FRENCH;}
		else {locale = Locale.ENGLISH;}
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
		return json;
	}
}