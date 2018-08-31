package org.jeesl.factory.json.db.tuple.t1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1TuplesFactory <A extends EjbWithId>
{
	private final Class<A> cT;
	
	private final UtilsFacade fUtils;
	
	private final Set<Long> setId;
	private final Json1TupleFactory<A> jtf;
	
	public Json1TuplesFactory(Class<A> cT) {this(null,cT);}
	public Json1TuplesFactory(UtilsFacade fUtils, Class<A> cT)
	{
		this.cT=cT;
		this.fUtils=fUtils;
		setId = new HashSet<Long>();
		jtf = new Json1TupleFactory<A>();
	}
	
	public List<Json1Tuple<A>> add(List<Json1Tuple<A>> list)
	{
		for(Json1Tuple<A> t : list)
		{
			if(!setId.contains(t.getId())) {setId.add(t.getId());}
		}
		return list;
	}
	
	public List<A> toTuple1List(UtilsFacade fUtils)
	{
		return fUtils.find(cT,setId);
	}
	
	public List<A> toListA(Json1Tuples<A> tuples)
	{
		Set<A> set = new HashSet<A>();
		for(Json1Tuple<A> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb())) {set.add(t.getEjb());}
		}
		return new ArrayList<A>(set);
	}
	
	public Json1Tuples<A> build(List<Tuple> tuples)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.build(t);
			setId.add(j.getId());
        	json.getTuples().add(j);
        }
		fillEjbs(json);	
		return json;
	}
	
	public Json1Tuples<A> buildSum(List<Tuple> tuples)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.buildSum(t);
			setId.add(j.getId());
        	json.getTuples().add(j);
        }
		fillEjbs(json);
		return json;
	}
	
	public Json1Tuples<A> buildCount(List<Tuple> tuples)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.buildCount(t);
			setId.add(j.getId());
        	json.getTuples().add(j);
        }
		fillEjbs(json);
		return json;
	}
	
	private void fillEjbs(Json1Tuples<A> json)
	{
		if(fUtils!=null)
		{
			Map<Long,A> map = EjbIdFactory.toIdMap(fUtils.find(cT,setId));
			for(Json1Tuple<A> t : json.getTuples())
			{
				t.setEjb(map.get(t.getId()));
			}
		}
	}
	
	public Map<A,Json1Tuple<A>> toMap(Json1Tuples<A> tuples)
	{
		Map<A,Json1Tuple<A>> map = new HashMap<A,Json1Tuple<A>>();
		
		for(Json1Tuple<A> t : tuples.getTuples())
		{
			if(!map.containsKey(t.getEjb())) {map.put(t.getEjb(), t);}
		}
		
		return map;
	}
}