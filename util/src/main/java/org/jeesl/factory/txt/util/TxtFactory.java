package org.jeesl.factory.txt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtFactory.class);
	
	public static String truncate(int length, String value)
	{
		if(value.length()<=length) {return value;}
		else
		{
			return value.substring(0, length);
		}
	}
}