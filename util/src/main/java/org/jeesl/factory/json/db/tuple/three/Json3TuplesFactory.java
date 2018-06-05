package org.jeesl.factory.json.db.tuple.three;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.db.tuple.three.Json3Tuple;
import org.jeesl.model.json.db.tuple.three.Json3Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json3TuplesFactory <A extends EjbWithId, B extends EjbWithId, C extends EjbWithId>
{
	private final UtilsFacade fUtils;
	
	private final Class<A> cA;
	private final Class<B> cY;
	private final Class<C> cC;
	
	private final Set<Long> setId1;
	private final Set<Long> setId2;
	private final Set<Long> setId3;
	private final Json3TupleFactory<A,B,C> jtf;
	
	public Json3TuplesFactory(Class<A> cA, Class<B> cB, Class<C> cC){this(null,cA,cB,cC);}
	public Json3TuplesFactory(UtilsFacade fUtils, Class<A> cA, Class<B> cY, Class<C> cC)
	{
		this.fUtils=fUtils;
		this.cA=cA;
		this.cY=cY;
		this.cC=cC;
		
		setId1 = new HashSet<Long>();
		setId2 = new HashSet<Long>();
		setId3 = new HashSet<Long>();
		jtf = new Json3TupleFactory<A,B,C>();
	}
	
	public Json3Tuples<A,B,C> buildSum(List<Tuple> tuples)
	{
		Json3Tuples<A,B,C> json = new Json3Tuples<A,B,C>();
		for(Tuple t : tuples)
        {
        	json.getTuples().add(jtf.buildSum(t));
        }
		fillEjbs(json);
		return json;
	}
	
	public Json3Tuples<A,B,C> buildCount(List<Tuple> tuples)
	{
		Json3Tuples<A,B,C> json = new Json3Tuples<A,B,C>();
		for(Tuple t : tuples)
        {
        	json.getTuples().add(jtf.buildCount(t));
        }
		fillEjbs(json);
		return json;
	}
	
	private void fillEjbs(Json3Tuples<A,B,C> json)
	{
		if(fUtils!=null)
		{
			Map<Long,A> map1 = EjbIdFactory.toIdMap(fUtils.find(cA,setId1));
			Map<Long,B> map2 = EjbIdFactory.toIdMap(fUtils.find(cY,setId2));
			Map<Long,C> map3 = EjbIdFactory.toIdMap(fUtils.find(cC,setId3));
			
			for(Json3Tuple<A,B,C> t : json.getTuples())
			{
				t.setEjb1(map1.get(t.getId1()));
				t.setEjb2(map2.get(t.getId2()));
				t.setEjb3(map3.get(t.getId3()));
			}
		}
	}
	
	public List<A> idToListA(Json3Tuples<A,B,C> tuples)
	{
		Set<Long> set = new HashSet<Long>();
		for(Json3Tuple<A,B,C> t : tuples.getTuples())
		{
			if(!set.contains(t.getId1())) {set.add(t.getId1());}
		}
		return fUtils.find(cA, set);
	}
	public List<A> toListA(Json3Tuples<A,B,C> tuples)
	{
		Set<A> set = new HashSet<A>();
		for(Json3Tuple<A,B,C> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb1())) {set.add(t.getEjb1());}
		}
		return new ArrayList<A>(set);
	}
	
	public List<B> toListB(Json3Tuples<A,B,C> tuples)
	{
		Set<B> set = new HashSet<B>();
		for(Json3Tuple<A,B,C> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb2())) {set.add(t.getEjb2());}
		}
		return new ArrayList<B>(set);
	}
}