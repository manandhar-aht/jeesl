package org.jeesl.factory.txt.util;

public class TxtBooleanFactory
{
	public static String yesNo(boolean value)
	{
		if(value) {return "Yes";}
		else {return "No";}
	}
}