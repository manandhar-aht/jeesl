package org.jeesl.controller.processor.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmountRounder
{
	final static Logger logger = LoggerFactory.getLogger(AmountRounder.class);
	
	public static double one(double value)
	{
		return Math.round(value*10)/10.0d;
	}
	
	public static double two(double value)
	{
		return Math.round(value*100)/100.0d;
	}
	
	public static double percentTwo(int a, int b)
	{
		double ratio = Integer.valueOf(a).doubleValue()/Integer.valueOf(b).doubleValue();
		return two(100d*ratio);
	}
}