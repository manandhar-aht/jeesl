package org.jeesl.factory.json.util.db.tuple.two;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.util.db.tuple.two.Json2Tuple;
import org.jeesl.model.json.util.db.tuple.two.Json2Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json2TuplesFactory <X extends EjbWithId, Y extends EjbWithId>
{
	private final UtilsFacade fUtils;
	
	private final Class<X> cX;
	private final Class<Y> cY;
	
	private final Set<Long> setId1;
	private final Set<Long> setId2;
	private final Json2TupleFactory<X,Y> jtf;
	
	public Json2TuplesFactory(Class<X> cX, Class<Y> cY){this(null,cX,cY);}
	public Json2TuplesFactory(UtilsFacade fUtils, Class<X> cX, Class<Y> cY)
	{
		this.fUtils=fUtils;
		this.cX=cX;
		this.cY=cY;
		
		setId1 = new HashSet<Long>();
		setId2 = new HashSet<Long>();
		jtf = new Json2TupleFactory<X,Y>();
	}
	
	public Json2Tuples<X,Y> buildSum(List<Tuple> tuples)
	{
		Json2Tuples<X,Y> json = new Json2Tuples<X,Y>();
		
		for(Tuple t : tuples)
        {
			Json2Tuple<X,Y> j = jtf.buildSum(t);
			setId1.add(j.getId1());
			setId2.add(j.getId2());
        	json.getTuples().add(j);
        }
		
		if(fUtils!=null)
		{
			Map<Long,X> map1 = EjbIdFactory.toIdMap(fUtils.find(cX,setId1));
			Map<Long,Y> map2 = EjbIdFactory.toIdMap(fUtils.find(cY,setId2));
			
			for(Json2Tuple<X,Y> t : json.getTuples())
			{
				t.setEjb1(map1.get(t.getId1()));
				t.setEjb2(map2.get(t.getId2()));
			}
		}
		
		return json;
	}
	
	public List<Y> toListY(Json2Tuples<X,Y> tuples)
	{
		Set<Y> set = new HashSet<Y>();
		for(Json2Tuple<X,Y> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb2())) {set.add(t.getEjb2());}
		}
		return new ArrayList<Y>(set);
	}
	
	public List<X> toListX(Json2Tuples<X,Y> tuples)
	{
		Set<X> set = new HashSet<X>();
		for(Json2Tuple<X,Y> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb1())) {set.add(t.getEjb1());}
		}
		return new ArrayList<X>(set);
	}
	
	public Map<X,Map<Y,Json2Tuple<X,Y>>> toMap(Json2Tuples<X,Y> tuples)
	{
		Map<X,Map<Y,Json2Tuple<X,Y>>> map = new HashMap<X,Map<Y,Json2Tuple<X,Y>>>();
		
		for(Json2Tuple<X,Y> t : tuples.getTuples())
		{
			if(!map.containsKey(t.getEjb1())) {map.put(t.getEjb1(), new HashMap<Y,Json2Tuple<X,Y>>());}
			map.get(t.getEjb1()).put(t.getEjb2(),t);
		}
		
		return map;
	}
	
	public static <X extends EjbWithId, Y extends EjbWithId> Map<Long,Map<Long,Json2Tuple<X,Y>>> toIdMap(Json2Tuples<X,Y> tuples)
	{
		Map<Long,Map<Long,Json2Tuple<X,Y>>> map = new HashMap<Long,Map<Long,Json2Tuple<X,Y>>>();
		
		for(Json2Tuple<X,Y> t : tuples.getTuples())
		{
			if(!map.containsKey(t.getId1())) {map.put(t.getId1(), new HashMap<Long,Json2Tuple<X,Y>>());}
			map.get(t.getId1()).put(t.getId2(),t);
		}
		
		return map;
	}
}