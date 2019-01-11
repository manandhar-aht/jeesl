package org.jeesl.util.query.ejb;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class MapListQuery
{
	final static Logger logger = LoggerFactory.getLogger(MapListQuery.class);
	
	public static <T extends EjbWithId, L extends EjbWithId> int size(Map<T,List<L>> map)
	{	
		int result = 0;
		for(List<L> list : map.values()){result = result+list.size();}
		return 1;
	}
}