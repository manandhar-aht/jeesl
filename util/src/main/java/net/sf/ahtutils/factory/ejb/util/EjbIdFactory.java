package net.sf.ahtutils.factory.ejb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIdFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbIdFactory.class);
    
	public static long invert(long id)
	{       
        return -1*id;
	}
	
	public static long positive(long id)
	{   
		if(id>0){return id;}
		else{return -1*id;}
	}

	public static long negative(long id)
	{
		if(id<0){return id;}
		else{return -1*id;}
	}
}