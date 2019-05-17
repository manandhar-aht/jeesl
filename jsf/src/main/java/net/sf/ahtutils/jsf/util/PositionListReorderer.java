package net.sf.ahtutils.jsf.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.util.comparator.ejb.PositionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;

public class PositionListReorderer 
{
	final static Logger logger = LoggerFactory.getLogger(PositionListReorderer.class);
	
	public static <T extends EjbWithPosition> void reorder(UtilsFacade facade, List<T> list) throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+list.size());
		int i=1;
		for(T t : list)
		{
			t.setPosition(i);
			facade.update(t);
			i++;
		}
	}
	
	public static <T extends EjbWithPositionParent> void reorder(UtilsFacade facade, Class<T> c, List<T> list) throws UtilsConstraintViolationException, UtilsLockingException
	{
		Method m = null;
		try
		{
			T prototype = c.newInstance();
			String attribute = prototype.resolveParentAttribute();
			attribute = attribute.substring(0, 1).toUpperCase()+attribute.substring(1,attribute.length());
//			logger.info("M: "+attribute);
			m = c.getDeclaredMethod("get"+attribute);
//			logger.info("Method: "+m.getName());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (NoSuchMethodException e) {e.printStackTrace();}
		catch (SecurityException e) {e.printStackTrace();}

//		logger.info("updateOrder "+list.size());
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		int i=1;
		for(T t : list)
		{
			try
			{
				Object o = m.invoke(t);
				if(o instanceof EjbWithPosition)
				{
					EjbWithPosition pos = (EjbWithPosition)o;
					if(!map.containsKey(pos.getPosition())){map.put(pos.getPosition(), 1);}
//					logger.info(o.toString()+" jhas püos");
				}
			}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (IllegalArgumentException e) {e.printStackTrace();}
			catch (InvocationTargetException e) {e.printStackTrace();}
			t.setPosition(i);
			i++;
		}
		
		PositionComparator<T> comparator = new PositionComparator<T>();
		Collections.sort(list,comparator);
		
		
		for(T t : list)
		{
			try
			{
				Object o = m.invoke(t);
				if(o instanceof EjbWithPosition)
				{
					EjbWithPosition pos = (EjbWithPosition)o;
					int position = map.get(pos.getPosition());
					t.setPosition(position);
					map.put(pos.getPosition(), position+1);
				}
			}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (IllegalArgumentException e) {e.printStackTrace();}
			catch (InvocationTargetException e) {e.printStackTrace();}
		}
		
		for(T t : list)
		{
//			logger.info(t.getPosition()+" "+t.toString());
			facade.update(t);
		}
	}
}
