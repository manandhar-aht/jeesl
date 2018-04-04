package org.jeesl.factory.json.util.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.model.json.util.db.JsonTuple1;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTupleFactory
{
	public static <T extends EjbWithId> List<Long> toIds(List<JsonTuple1<T>> list)
	{
		List<Long> result = new ArrayList<Long>();
		for(JsonTuple1<T> t : list)
		{
			result.add(t.getId());
		}
		return result;
	}
	
	public static <T extends EjbWithId> Map<Long,JsonTuple1<T>> toMap(List<JsonTuple1<T>> list)
	{
		Map<Long,JsonTuple1<T>> map = new HashMap<Long,JsonTuple1<T>>();
		for(JsonTuple1<T> t : list)
		{
			map.put(t.getId(),t);
		}
		return map;
	}
}