package org.jeesl.factory.json.db.tuple.two;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.db.tuple.two.Json2Tuple;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json2TuplesFactory <A extends EjbWithId, B extends EjbWithId>
{
	private final UtilsFacade fUtils;
	
	private final Class<A> cA;
	private final Class<B> cY;
	
	private final Set<Long> setId1;
	private final Set<Long> setId2;
	private final Json2TupleFactory<A,B> jtf;
	
	public Json2TuplesFactory(Class<A> cA, Class<B> cY){this(null,cA,cY);}
	public Json2TuplesFactory(UtilsFacade fUtils, Class<A> cA, Class<B> cY)
	{
		this.fUtils=fUtils;
		this.cA=cA;
		this.cY=cY;
		
		setId1 = new HashSet<Long>();
		setId2 = new HashSet<Long>();
		jtf = new Json2TupleFactory<A,B>();
	}
	
	public Json2Tuples<A,B> buildSum(List<Tuple> tuples)
	{
		Json2Tuples<A,B> json = new Json2Tuples<A,B>();
		
		for(Tuple t : tuples)
        {
			Json2Tuple<A,B> j = jtf.buildSum(t);
			setId1.add(j.getId1());
			setId2.add(j.getId2());
        	json.getTuples().add(j);
        }
		
		fillEjbs(json);
		
		return json;
	}
	
	public Json2Tuples<A,B> buildCount(List<Tuple> tuples)
	{
		Json2Tuples<A,B> json = new Json2Tuples<A,B>();
		
		for(Tuple t : tuples)
        {
			Json2Tuple<A,B> j = jtf.buildCount(t);
			setId1.add(j.getId1());
			setId2.add(j.getId2());
        	json.getTuples().add(j);
        }
		
		fillEjbs(json);
		
		return json;
	}
	
	private void fillEjbs(Json2Tuples<A,B> json)
	{
		if(fUtils!=null)
		{
			Map<Long,A> map1 = EjbIdFactory.toIdMap(fUtils.find(cA,setId1));
			Map<Long,B> map2 = EjbIdFactory.toIdMap(fUtils.find(cY,setId2));
			
			for(Json2Tuple<A,B> t : json.getTuples())
			{
				t.setEjb1(map1.get(t.getId1()));
				t.setEjb2(map2.get(t.getId2()));
			}
		}
	}
	
	public List<A> idToListX(Json2Tuples<A,B> tuples)
	{
		Set<Long> set = new HashSet<Long>();
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(!set.contains(t.getId1())) {set.add(t.getId1());}
		}
		return fUtils.find(cA, set);
	}
	public List<A> toListX(Json2Tuples<A,B> tuples)
	{
		Set<A> set = new HashSet<A>();
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb1())) {set.add(t.getEjb1());}
		}
		return new ArrayList<A>(set);
	}
	
	public List<B> toListY(Json2Tuples<A,B> tuples)
	{
		Set<B> set = new HashSet<B>();
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb2())) {set.add(t.getEjb2());}
		}
		return new ArrayList<B>(set);
	}
	
	public Map<A,Map<B,Json2Tuple<A,B>>> toMap(Json2Tuples<A,B> tuples)
	{
		Map<A,Map<B,Json2Tuple<A,B>>> map = new HashMap<A,Map<B,Json2Tuple<A,B>>>();
		
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(!map.containsKey(t.getEjb1())) {map.put(t.getEjb1(), new HashMap<B,Json2Tuple<A,B>>());}
			map.get(t.getEjb1()).put(t.getEjb2(),t);
		}
		
		return map;
	}
	
	public static <A extends EjbWithId, B extends EjbWithId> Map<Long,Map<Long,Json2Tuple<A,B>>> toIdMap(Json2Tuples<A,B> tuples)
	{
		Map<Long,Map<Long,Json2Tuple<A,B>>> map = new HashMap<Long,Map<Long,Json2Tuple<A,B>>>();
		
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(!map.containsKey(t.getId1())) {map.put(t.getId1(), new HashMap<Long,Json2Tuple<A,B>>());}
			map.get(t.getId1()).put(t.getId2(),t);
		}
		
		return map;
	}
}