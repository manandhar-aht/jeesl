package org.jeesl.factory.ejb.util;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCodeFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbCodeFactory.class);
	    
    public static <T extends EjbWithCode> Map<String,T> toMapCode(List<T> list)
    {
	    	Map<String,T> map = new Hashtable<String,T>();
	    	for(T s : list)
	    	{
	    		map.put(s.getCode(), s);
	    	}
        return map;
    }
}