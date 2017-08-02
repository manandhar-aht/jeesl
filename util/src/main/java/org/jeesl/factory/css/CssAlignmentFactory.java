package org.jeesl.factory.css;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CssAlignmentFactory
{
	final static Logger logger = LoggerFactory.getLogger(CssAlignmentFactory.class);
    	
	public static void appendTextCenter(StringBuilder sb)
	{
		if(sb!=null)
		{
			sb.append(" text-align: center;");
		}
	}
}