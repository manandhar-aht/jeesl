package org.jeesl.factory.json.db.tuple.t2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.db.tuple.two.Json2Tuple;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json2TuplesFactory <A extends EjbWithId, B extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(Json2TuplesFactory.class);
	
	private final UtilsFacade fUtils;
	private final Json2TupleFactory<A,B> jtf;
	
	private final Class<A> cA; public Class<A> getClassA() {return cA;}
	private final Class<B> cB; public Class<B> getClassB() {return cB;}
	
	private final Set<Long> setId1;
	private final Set<Long> setId2;
	
	protected final List<A> listA; public List<A> getListA() {return listA;}
	protected final List<B> listB; public List<B> getListB() {return listB;}
	
	protected final Map<A,Map<B,Json2Tuple<A,B>>> map2; public Map<A, Map<B, Json2Tuple<A, B>>> getMap2() {return map2;}
	protected final Map<Long,A> mapA; public Map<Long,A> getMapA() {return mapA;}
	protected final Map<Long,B> mapB; public Map<Long,B> getMapB() {return mapB;}
	
	private Json2Tuples<A,B> tuples; public Json2Tuples<A,B> get2Tuples() {return tuples;} public void set2Tuples(Json2Tuples<A,B> tuples) {this.tuples = tuples;}

	
	public Json2TuplesFactory(Class<A> cA, Class<B> cY){this(null,cA,cY);}
	public Json2TuplesFactory(UtilsFacade fUtils, Class<A> cA, Class<B> cY)
	{
		this.fUtils=fUtils;
		this.cA=cA;
		this.cB=cY;
		
		setId1 = new HashSet<Long>();
		setId2 = new HashSet<Long>();
		
		listA = new ArrayList<A>();
		listB = new ArrayList<B>();
		
		map2 = new HashMap<A,Map<B,Json2Tuple<A,B>>>();
		mapA = new HashMap<Long,A>();
		mapB = new HashMap<Long,B>();
		
		jtf = new Json2TupleFactory<A,B>();
	}
	
	protected void clear()
	{
		setId1.clear();
		setId2.clear();
		
		listA.clear();
		listB.clear();
		
		map2.clear();
		mapA.clear();
		mapB.clear();
	}
	
	public void init(UtilsFacade fUtils, Json2Tuples<A,B> json)
	{
		clear();
		this.tuples = json;
		
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			setId1.add(t.getId1());
			setId2.add(t.getId2());
		}
		
		listA.addAll(fUtils.find(cA, setId1));
		listB.addAll(fUtils.find(cB, setId2));
		
		if(setId1.size()!=listA.size()) {logger.warn("Not all elements of "+cA.getSimpleName()+" can be retrieved.");}
		if(setId2.size()!=listB.size()) {logger.warn("Not all elements of "+cB.getSimpleName()+" can be retrieved.");}
		
		mapA.putAll(EjbIdFactory.toIdMap(listA));
		mapB.putAll(EjbIdFactory.toIdMap(listB));
		
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(!map2.containsKey(t.getEjb1())) {map2.put(t.getEjb1(), new HashMap<B,Json2Tuple<A,B>>());}
			map2.get(t.getEjb1()).put(t.getEjb2(),t);
		}
	}
	
	private void fillEjbs(Json2Tuples<A,B> json)
	{
		if(fUtils!=null)
		{
			Map<Long,A> map1 = EjbIdFactory.toIdMap(fUtils.find(cA,setId1));
			Map<Long,B> map2 = EjbIdFactory.toIdMap(fUtils.find(cB,setId2));
			
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
	public List<B> toListB()
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
	
	public boolean map2Contains(A a, B b)
	{
		return map2.containsKey(a) && map2.get(a).containsKey(b);
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
	
	// This method is used if a third grouping is added to the query. Then it's counted on the unique id1-id2 combination
	public Json2Tuples<A,B> countOnIds(List<Tuple> tuples)
	{
		Map<MultiKey,Json2Tuple<A,B>> mapTuples = new HashMap<MultiKey,Json2Tuple<A,B>>();
		Map<MultiKey,Long> mapCount = new HashMap<MultiKey,Long>();
		
		for(Tuple t : tuples)
        {
			Json2Tuple<A,B> j = jtf.buildCount(t);
			setId1.add(j.getId1());
			setId2.add(j.getId2());
			
			MultiKey key = new MultiKey(j.getId1(),j.getId2());
			if(!mapTuples.containsKey(key)) {mapTuples.put(key, j);}
			if(mapCount.containsKey(key))
			{
				mapCount.put(key, 1+mapCount.get(key));
			}
			else
			{
				mapCount.put(key, 1l);
			}
        }
		
		Json2Tuples<A,B> json = new Json2Tuples<A,B>();
		for(MultiKey key : mapTuples.keySet())
		{
			Json2Tuple<A,B> t = mapTuples.get(key);
			t.setCount(mapCount.get(key));
			json.getTuples().add(t);
		}
		
		fillEjbs(json);
		
		return json;
	}
}