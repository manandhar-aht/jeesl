package org.jeesl.factory.json.util.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.model.json.util.db.JsonTuple1;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuplesFactory <T extends EjbWithId>
{
	private final Class<T> cT;
	private final Set<Long> setId1;
	
	public JsonTuplesFactory(Class<T> cT)
	{
		this.cT=cT;
		setId1 = new HashSet<Long>();
	}
	
	public List<JsonTuple1<T>> add(List<JsonTuple1<T>> list)
	{
		for(JsonTuple1<T> t : list)
		{
			if(!setId1.contains(t.getId())) {setId1.add(t.getId());}
		}
		return list;
	}
	
	public List<T> toTuple1List(UtilsFacade fUtils)
	{
		return fUtils.find(cT,setId1);
	}
}