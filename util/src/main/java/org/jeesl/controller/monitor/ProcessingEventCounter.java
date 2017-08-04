package org.jeesl.controller.monitor;

import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingEventCounter
{
	final static Logger logger = LoggerFactory.getLogger(ProcessingEventCounter.class);
	
	private String category;
	private Map<String,Integer> map;
	
	public ProcessingEventCounter(){this("Default Category");}
	public ProcessingEventCounter(String category)
	{
		this.category=category;
		map = new Hashtable<String,Integer>();
	}
	
	public <E extends Enum<E>> void add(E event){add(event.toString());}
	public void add(String event)
	{
		if(!map.containsKey(event)){map.put(event, 0);}
		map.put(event, map.get(event)+1);
	}
	
	public <E extends Enum<E>> int events(E event){return events(event.toString());}
	public int events(String event)
	{
		if(map.containsKey(event)){return map.get(event);}
		else{return 0;}
	}
	
	public void debug()
	{
		
		logger.info("Events in category "+category+": "+toTotalEvents());
		for(String key : map.keySet())
		{
			logger.info("\t"+key+": "+map.get(key));
		}
	}
	
	private int toTotalEvents()
	{
		int i=0;
		for(String key : map.keySet())
		{
			i = i+map.get(key);
		}
		return i;
	}
}
