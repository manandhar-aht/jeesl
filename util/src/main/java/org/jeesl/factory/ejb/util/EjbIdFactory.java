package org.jeesl.factory.ejb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

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
	
	public static boolean isUnSaved(EjbWithId ejb){return !isSaved(ejb);}
	public static boolean isSaved(EjbWithId ejb){return ejb.getId()>0;}
	
	public static <T extends EjbWithId> List<Long> toIds(List<T> list){return toIds(list,null);}
	public static <T extends EjbWithId> List<Long> toIds(List<T> list, Boolean onlySaved)
	{
		List<Long> results = new ArrayList<Long>();
		for(T ejb : list)
		{
			if(onlySaved==null){results.add(ejb.getId());}
			else
			{
				if(!onlySaved){results.add(ejb.getId());}
				else if(onlySaved && EjbIdFactory.isSaved(ejb)){results.add(ejb.getId());}
			}
		}
		return results;
	}
	
	public static <T extends EjbWithId> Map<Long,T> toIdMap(List<T> list)
	{
		Map<Long,T> map = new HashMap<Long,T>();
		for(T t : list){map.put(t.getId(), t);}
		return map;
	}
	
	public static <T extends EjbWithId> void setNextNegativeId(T ejb, List<T> list)
	{
		Set<Long> existing = new HashSet<Long>(EjbIdFactory.toIds(list));
		
		boolean search = true;
		long next = 0-existing.size();
		while(search)
		{
			if(existing.contains(next)){next--;}
			else{search=false;}
		}
		ejb.setId(next);
	}
	public static <T extends EjbWithId> void negativeToZero(List<T> list)
	{
		for(T ejb : list)
		{
			if(ejb.getId()<0){ejb.setId(0);}
		}
	}
}