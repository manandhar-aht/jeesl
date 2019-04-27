package org.jeesl.util.db.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbIdMapCache <T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIdMapCache.class);

	private final UtilsFacade fUtils;
	private final Class<T> c;
	
	private final Map<Long,T> map;
	
	public EjbIdMapCache(UtilsFacade fUtils, Class<T> c)
	{
		this.fUtils=fUtils;
		this.c=c;
		map = new HashMap<>();
	}
	
	public T ejb(long id)
	{
		if(!map.containsKey(id))
		{
			try
			{
				map.put(id, fUtils.find(c,id));
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
			
		}
		return map.get(id);
	}
}