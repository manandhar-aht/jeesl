package org.jeesl.controller.processor.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmountRounder
{
	final static Logger logger = LoggerFactory.getLogger(AmountRounder.class);
	
	public static double two(double value)
	{
		return Math.round(value*100)/100.0d;
	}
}