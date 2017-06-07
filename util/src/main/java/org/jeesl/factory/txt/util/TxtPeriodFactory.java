package org.jeesl.factory.txt.util;

import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtPeriodFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtPeriodFactory.class);
	
	public static enum UNITS{hourMinute}
	
	private PeriodFormatter periodFormatter;
	
	PeriodType pt;
	
	public TxtPeriodFactory()
	{
		periodFormatter = new PeriodFormatterBuilder()
				.appendWeeks().appendSuffix(" week", " weeks").appendSeparator(" ")
			    .appendDays().appendSuffix(" day", " days").appendSeparator(" ")
			    .appendHours().appendSuffix("hr", "hrs").appendSeparator(" ")
			    .appendMinutes().appendSuffix("min", "mins").appendSeparator(" ")
			    .appendSeconds().appendSuffix(" second", " secs").toFormatter();
		setUnits(UNITS.hourMinute);
	}
	
	public void setUnits(UNITS units)
	{
		DurationFieldType[] types=null;
		switch(units)
		{
			case hourMinute: types = new DurationFieldType[]{DurationFieldType.hours(),DurationFieldType.minutes()};break;
		}
		pt = PeriodType.forFields(types);
	}
	
	public String debug(int minutes)
	{
		Period p = Period.minutes(minutes);
		return periodFormatter.print(p.normalizedStandard(pt));
	}
	
	
}