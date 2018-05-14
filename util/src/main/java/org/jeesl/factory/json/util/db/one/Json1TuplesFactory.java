package org.jeesl.factory.json.util.db.one;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.util.db.one.Json1Tuple;
import org.jeesl.model.json.util.db.one.Json1Tuples;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1TuplesFactory <T extends EjbWithId>
{
	private final Class<T> cT;
	
	private final UtilsFacade fUtils;
	
	private final Set<Long> setId;
	private final Json1TupleFactory<T> jtf;
	
	public Json1TuplesFactory(Class<T> cT) {this(null,cT);}
	public Json1TuplesFactory(UtilsFacade fUtils, Class<T> cT)
	{
		this.cT=cT;
		this.fUtils=fUtils;
		setId = new HashSet<Long>();
		jtf = new Json1TupleFactory<T>();
	}
	
	public List<Json1Tuple<T>> add(List<Json1Tuple<T>> list)
	{
		for(Json1Tuple<T> t : list)
		{
			if(!setId.contains(t.getId())) {setId.add(t.getId());}
		}
		return list;
	}
	
	public List<T> toTuple1List(UtilsFacade fUtils)
	{
		return fUtils.find(cT,setId);
	}
	
	public Json1Tuples<T> buildSum(List<Tuple> tuples)
	{
		Json1Tuples<T> json = new Json1Tuples<T>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<T> j = jtf.buildSum(t);
			setId.add(j.getId());
        	json.getTuples().add(j);
        }
		
		if(fUtils!=null)
		{
			Map<Long,T> map = EjbIdFactory.toIdMap(fUtils.find(cT,setId));
			for(Json1Tuple<T> t : json.getTuples())
			{
				t.setEjb(map.get(t.getId()));
			}
		}
		
		return json;
	}
}