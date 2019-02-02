package org.jeesl.factory.json.db.tuple.t1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.db.tuple.JsonTuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1TuplesFactory <A extends EjbWithId>
{
	private final Class<A> cA; public Class<A> getClassA() {return cA;}

	private UtilsFacade fUtils; public void setfUtils(UtilsFacade fUtils) {this.fUtils = fUtils;}

	private final Set<Long> setId;
	private final Json1TupleFactory<A> jtf;
	
	protected final Map<Long,A> mapA; public Map<Long,A> getMapA() {return mapA;}
	
	private Json1Tuples<A> tuples; public Json1Tuples<A> get1Tuples() {return tuples;} public void set1Tuples(Json1Tuples<A> tuples) {this.tuples = tuples;}

	
	public Json1TuplesFactory(Class<A> cA) {this(null,cA);}
	public Json1TuplesFactory(UtilsFacade fUtils, Class<A> cA)
	{
		this.cA=cA;
		this.fUtils=fUtils;
		setId = new HashSet<Long>();
		mapA = new HashMap<Long,A>();
		
		jtf = new Json1TupleFactory<A>();
	}
	
	public void init(UtilsFacade fUtils, Json1Tuples<A> json)
	{
		clear();
		this.tuples = json;
		
		for(Json1Tuple<A> t : json.getTuples())
		{
			setId.add(t.getId());
		}
		
		mapA.putAll(EjbIdFactory.toIdMap(fUtils.find(cA, setId)));
	}
	
	protected void clear()
	{
		setId.clear();
		
		mapA.clear();
	}
	
	public List<A> toListA()
	{
		return new ArrayList<A>(mapA.values());
	}
	
	// Deprecated?
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
		return fUtils.find(cA,setId);
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
	
	public Json1Tuples<A> build(List<Tuple> tuples, JsonTuple.Field... fields)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.build(t,fields);
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
			Map<Long,A> map = EjbIdFactory.toIdMap(fUtils.find(cA,setId));
			for(Json1Tuple<A> t : json.getTuples())
			{
				t.setEjb(map.get(t.getId()));
			}
		}
	}
	
	@Deprecated
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