package org.jeesl.model.pojo.map.id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Nested2IdMap <VALUE extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(Nested2IdMap.class);

    private Map<Long,Map<Long,VALUE>> m; public Map<Long,Map<Long,VALUE>> getM() {return m;}
    
    public Nested2IdMap()
    {
		m = new HashMap<Long,Map<Long,VALUE>>();
    }
    
    public void clear()
    {
    	for(Map<Long,VALUE> m : m.values())
    	{
    		m.clear();
    	}
    	m.clear();
    }
    
    public void put(Long l1, Long l2, VALUE value)
	{
		if(!m.containsKey(l1)){m.put(l1,new HashMap<Long,VALUE>());}
		m.get(l1).put(l2,value);
	}
    
    public boolean containsKey(Long l1, Long l2)
    {
    	return (m.containsKey(l1) && m.get(l1).containsKey(l2));
    }
    
    public List<VALUE> values()
    {
    	List<VALUE> list = new ArrayList<VALUE>();
    	for(Map<Long,VALUE> map : m.values())
    	{
    		list.addAll(map.values());
    	}
    	return list;
    }
	
	public VALUE get(Long l1, Long l2)
	{
		return m.get(l1).get(l2);
	}
}