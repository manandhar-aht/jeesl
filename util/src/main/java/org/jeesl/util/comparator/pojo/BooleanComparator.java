package org.jeesl.util.comparator.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BooleanComparator
{
	final static Logger logger = LoggerFactory.getLogger(BooleanComparator.class);
    
    public static boolean active(Boolean b)
    {
        if(b==null){return false;}
        else {return b;}
    }
}