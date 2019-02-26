package org.jeesl.factory.json.db.tuple.t1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.jeesl.model.json.db.tuple.JsonTuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;

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
	
	public Json1Tuple<A> buildCount(Tuple tuple)
	{
		Json1Tuple<A> json = build(tuple);
		json.setCount((Long)tuple.get(1));
    	return json;
	}
	
	public Json1Tuple<A> buildCountNative(Object object)
	{
		Object[] array = (Object[])object;
        long id = ((BigInteger)array[0]).longValue();
        long count = ((Integer)array[1]).longValue();
        
        Json1Tuple<A> json = new Json1Tuple<A>();
        json.setId(id);
        json.setCount(count);
        return json;
	}
	
	public Json1Tuple<A> build(Tuple tuple, JsonTuple.Field... fields)
	{
		Json1Tuple<A> json = build(tuple);
		
		int index=1;
		for(JsonTuple.Field field : fields)
		{
			if(index==1)
			{
				if(field.equals(JsonTuple.Field.sum)) {json.setSum1((Double)tuple.get(index));}
			}
			if(index==2)
			{
				if(field.equals(JsonTuple.Field.sum)) {json.setSum2((Double)tuple.get(index));}
			}
			index++;
		}
    	return json;
	}
}