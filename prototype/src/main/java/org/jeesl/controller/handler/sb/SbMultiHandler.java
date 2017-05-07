package org.jeesl.controller.handler.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.io.StringUtil;

public class SbMultiHandler <T extends EjbWithId>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SbMultiHandler.class);
	private static final long serialVersionUID = 1L;

	private final Class<T> cT;
	private final SbToggleBean bean; 
	
	private List<T> list;public List<T> getList() {return list;} public void setList(List<T> list) {this.list = list;}
	private final List<T> selected;public List<T> getSelected() {return selected;}
	private Map<T,Boolean> map;public Map<T,Boolean> getMap() {return map;}
	
	public SbMultiHandler(final Class<T> cT, List<T> list){this(cT,list,null);}
	public SbMultiHandler(final Class<T> cT, SbToggleBean bean){this(cT,new ArrayList<T>(),bean);}
	public SbMultiHandler(final Class<T> cT, List<T> list, SbToggleBean bean)
	{
		this.cT=cT;
		this.list=list;
		this.bean=bean;
		map = new ConcurrentHashMap<T,Boolean>();
		selected = new ArrayList<T>();
		refresh();
	}
	
	public void clear()
	{
		list.clear();
		selected.clear();
		map.clear();
	}
	
	public void toggleNone(){selectNone();callbackToggledToBean();}
	public void selectNone()
	{
		for(T t : list){map.put(t,false);}
		refresh();
	}
	
	public void toggleAll(){selectAll();callbackToggledToBean();}
	public void selectAll()
	{
		for(T t : list){map.put(t,true);}
		refresh();
	}
	
	public void select(T t)
	{
		map.put(t, true);
		refresh();
	}

	public void toggle(T type)
	{
		if(!map.containsKey(type)){map.put(type,true);}
		else{map.put(type,!map.get(type));}
		refresh();
		callbackToggledToBean();

	}
	
	private void callbackToggledToBean()
	{
		try {if(bean!=null){bean.toggled(cT);}}
		catch (UtilsLockingException e) {e.printStackTrace();}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
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
	
	public boolean isSelected(T t)
	{
		return map.containsKey(t) && map.get(t);
	}
	
	public boolean hasSelected(){return !selected.isEmpty();}
	public boolean allSelected(){return selected.size()==list.size();}

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
				logger.info("\t"+map.get(t)+" "+t.toString()+" "+selected.contains(t));
			}
		}
	}
}