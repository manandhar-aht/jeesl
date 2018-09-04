package org.jeesl.jsf.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslLazyListHandler <T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslLazyListHandler.class);
	
	public JeeslLazyListHandler()
	{
		
	}
	
	public void paginator(List<T> data, List<T> tmp, int first, int pageSize)
	{
		data.clear();
		if(tmp.size() > pageSize)
		{
			try
			{
				if (first+pageSize < tmp.size()){data.addAll(tmp.subList(first, first + pageSize));}
				else{data.addAll(tmp.subList(first, tmp.size()-1));}
			}
			catch(IndexOutOfBoundsException e){data.addAll(tmp.subList(first, first + (tmp.size() % pageSize)));}
		}
		else {data.addAll(tmp);}
	}
	
	public Object getRowKey(T t) {return t.getId();}
	
	public T getRowData(List<T> list, String rowKey)
	{
		long id = new Long(rowKey).longValue();
		for(T t : list)
		{
			if(t.getId()==id)
			{
				return t;
			}
		}
		return null;
	}
}