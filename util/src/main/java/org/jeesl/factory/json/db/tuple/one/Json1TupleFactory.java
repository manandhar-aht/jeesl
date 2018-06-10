package org.jeesl.factory.json.db.tuple.one;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.jeesl.model.json.db.tuple.one.Json1Tuple;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1TupleFactory<A extends EjbWithId>
{
	public Json1TupleFactory()
	{
		
	}
	
	public static <T extends EjbWithId> List<Long> toIds(List<Json1Tuple<T>> list)
	{
		List<Long> result = new ArrayList<Long>();
		for(Json1Tuple<T> t : list)
		{
			result.add(t.getId());
		}
		return result;
	}
		
	public Json1Tuple<A> build(Tuple tuple)
	{
		Json1Tuple<A> json = new Json1Tuple<A>();
		json.setId((Long)tuple.get(0));		
    	return json;
	}
	
	public Json1Tuple<A> buildSum(Tuple tuple)
	{
		Json1Tuple<A> json = build(tuple);	
		json.setSum((Double)tuple.get(1));
    	return json;
	}
	
	public static <T extends EjbWithId> Map<Long,Json1Tuple<T>> toIdMap(List<Json1Tuple<T>> list)
	{
		Map<Long,Json1Tuple<T>> map = new HashMap<Long,Json1Tuple<T>>();
		for(Json1Tuple<T> t : list)
		{
			map.put(t.getId(),t);
		}
		return map;
	}
}