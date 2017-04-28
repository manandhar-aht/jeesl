package org.jeesl.factory.txt.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeesl.model.json.util.Day;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtDayFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtDayFactory.class);
		
	public static String daysOfMonths(List<Day> days)
	{
		List<Integer> list = new ArrayList<Integer>();
		if(days!=null)
		{
			for(Day day : days)
			{
				list.add(day.getNr());
			}
		}
		
		return StringUtils.join(list,", ");
	}
}