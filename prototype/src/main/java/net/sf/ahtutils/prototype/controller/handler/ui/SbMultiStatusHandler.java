package net.sf.ahtutils.prototype.controller.handler.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.io.StringUtil;

public class SbMultiStatusHandler <L extends UtilsLang,D extends UtilsDescription, T extends UtilsStatus<T,L,D>>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SbMultiStatusHandler.class);
	private static final long serialVersionUID = 1L;

	private final Class<T> cT;
	
	private List<T> list;public List<T> getList() {return list;}public void setList(List<T> list) {this.list = list;}
	private List<T> selected;public List<T> getSelected() {return selected;}
	private Map<T,Boolean> map;public Map<T,Boolean> getMap() {return map;}

	public SbMultiStatusHandler(final Class<T> cT, List<T> list)
	{
		this.cT=cT;
		this.list=list;
		map = new ConcurrentHashMap<T,Boolean>();
		selected = new ArrayList<T>();
		refresh();
	}
	
	public void selectAll()
	{
		for(T t : list){map.put(t, true);}
		refresh();
	}
	
	@SuppressWarnings("unchecked")
	public void multiToggle(Object o)
	{
		if(o.getClass().isAssignableFrom(cT)){toggle((T)o);}
	}

	public void toggle(T type)
	{
		if(!map.containsKey(type)){map.put(type,true);}
		else
		{
			{map.put(type,!map.get(type));}
		}
		refresh();
	}

	private void refresh()
	{
		selected.clear();
		for(T t : list)
		{
			if(!map.containsKey(t)) {map.put(t,false);}
			if(map.get(t)){selected.add(t);}
		}
	}

	public void debug(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("List "+list.size());
			logger.info("Selected "+selected.size());
			logger.info("Map "+map.size());
			for(T t : list)
			{
				logger.info("\t"+map.get(t)+" "+t.getCode()+" "+selected.contains(t));
			}
		}
	}
}