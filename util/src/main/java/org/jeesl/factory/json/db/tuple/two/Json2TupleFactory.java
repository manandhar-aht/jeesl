package org.jeesl.factory.json.db.tuple.two;

import javax.persistence.Tuple;

import org.jeesl.model.json.db.tuple.two.Json2Tuple;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json2TupleFactory<X extends EjbWithId, Y extends EjbWithId>
{
	public Json2TupleFactory()
	{
		
	}
	
	public Json2Tuple<X,Y> buildSum(Tuple tuple)
	{
		Json2Tuple<X,Y> json = new Json2Tuple<X,Y>();
		json.setId1((Long)tuple.get(0));
		json.setId2((Long)tuple.get(1));
		json.setSum((Double)tuple.get(2));
    	return json;
	}
	
	public Json2Tuple<X,Y> buildCount(Tuple tuple)
	{
		Json2Tuple<X,Y> json = new Json2Tuple<X,Y>();
		json.setId1((Long)tuple.get(0));
		json.setId2((Long)tuple.get(1));
		json.setCount((Long)tuple.get(2));
    	return json;
	}
}