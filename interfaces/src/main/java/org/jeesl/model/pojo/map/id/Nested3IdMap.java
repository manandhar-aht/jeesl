package org.jeesl.model.pojo.map.id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Nested3IdMap <VALUE extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(Nested3IdMap.class);

    private Map<Long,Nested2IdMap<VALUE>> m; public Map<Long, Nested2IdMap<VALUE>> getM() {return m;}

	public Nested3IdMap()
    {
		m = new HashMap<Long,Nested2IdMap<VALUE>>();
    }
	
	public void clear()
	{
		for(Nested2IdMap<VALUE> n2 : m.values())
		{
			n2.clear();
		}
		m.clear();
		
	}
	
	public void put(Long l1, Long l2, Long l3, VALUE value)
	{
		if(!m.containsKey(l1)){m.put(l1,new Nested2IdMap<VALUE>());}
		m.get(l1).put(l2,l3,value);
	}
	
	public void putIfEmpty(Long l1, Long l2, Long l3, VALUE value)
	{
		if(!this.containsKey(l1,l2,l3))
		{
			this.put(l1,l2,l3,value);
		}
	}
	
	public boolean containsKey(Long l1, Long l2, Long l3)
    {
    	return (m.containsKey(l1) && m.get(l1).containsKey(l2,l3));
    }
	
	public List<VALUE> values(Long l1)
	{
		List<VALUE> list = new ArrayList<VALUE>();
		
		if(m.containsKey(l1))
		{
			Nested2IdMap<VALUE> n2 = m.get(l1);
			list.addAll(n2.values());
		}
		
		return list;
	}
}