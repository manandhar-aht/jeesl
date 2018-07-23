package org.jeesl.factory.json.db.tuple.t4;

import javax.persistence.Tuple;

import org.jeesl.model.json.db.tuple.t4.Json4Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json4TupleFactory<A extends EjbWithId, B extends EjbWithId, C extends EjbWithId, D extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(Json4TupleFactory.class);
	
	public Json4TupleFactory()
	{
		
	}
	
	public Json4Tuple<A,B,C,D> buildSum(Tuple tuple)
	{
		Json4Tuple<A,B,C,D> json = build(tuple);
		json.setSum((Double)tuple.get(4));
    	return json;
	}
	
	public Json4Tuple<A,B,C,D> buildCount(Tuple tuple)
	{
		Json4Tuple<A,B,C,D> json = build(tuple);
		json.setCount((Long)tuple.get(4));
    	return json;
	}
	
	private Json4Tuple<A,B,C,D> build(Tuple tuple)
	{
		Json4Tuple<A,B,C,D> json = new Json4Tuple<A,B,C,D>();
		json.setId1((Long)tuple.get(0));
		json.setId2((Long)tuple.get(1));
		json.setId3((Long)tuple.get(2));
		json.setId4((Long)tuple.get(3));
		return json;
	}
}