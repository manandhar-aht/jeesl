package org.jeesl.util.db.cache;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class EjbCodeCache <T extends EjbWithCode>
{
	final static Logger logger = LoggerFactory.getLogger(EjbCodeCache.class);

	private final UtilsFacade fUtils;
	private final Class<T> c;
	
	private final Map<String,T> map;
	
	public EjbCodeCache(UtilsFacade fUtils, Class<T> c)
	{
		this.fUtils=fUtils;
		this.c=c;
		map = new HashMap<>();
	}
	
	public <E extends Enum<E>> T ejb(E code) {return ejb(code.toString());}
	public T ejb(String code)
	{
		if(!map.containsKey(code))
		{
			try
			{
				map.put(code, fUtils.fByCode(c,code));
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
		}
		return map.get(code);
	}
}