package org.jeesl.factory.ejb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbStringFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbStringFactory.class);
	   
	
    public static boolean empty(String... strings)
    {
	    for(String s : strings)
	    {
	    	if(s==null || s.trim().length()==0) {return true;}
	    }
        return false;
    }
}