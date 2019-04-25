package org.jeesl.factory.ejb.module.ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTsDataPointFactory<MP extends JeeslTsMultiPoint<?,?,?,?>,
								DATA extends JeeslTsData<?,?,?,?>,
								POINT extends JeeslTsDataPoint<DATA,MP>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsDataPointFactory.class);
	
	private final Class<POINT> cPoint;
    
	public EjbTsDataPointFactory(final Class<POINT> cPoint)
	{       
        this.cPoint=cPoint;
	}
	
	public POINT build(DATA data, MP multiPoint, Double value)
	{
		POINT ejb = null;
		try
		{
			ejb = cPoint.newInstance();
			ejb.setData(data);
			ejb.setMultiPoint(multiPoint);
			ejb.setValue(value);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
	
	public Map<DATA,List<POINT>> toMapData(List<POINT> list)
	{
		Map<DATA,List<POINT>> map = new HashMap<DATA,List<POINT>>();
		for(POINT p : list)
		{
			if(!map.containsKey(p.getData())) {map.put(p.getData(),new ArrayList<POINT>());}
			map.get(p.getData()).add(p);
			
		}
		return map;
	}
	
	public Map<MP,List<POINT>> toMapMultiPoint(List<POINT> list)
	{
		Map<MP,List<POINT>> map = new HashMap<MP,List<POINT>>();
		for(POINT p : list)
		{
			if(!map.containsKey(p.getMultiPoint())) {map.put(p.getMultiPoint(),new ArrayList<POINT>());}
			map.get(p.getMultiPoint()).add(p);
			
		}
		return map;
	}
	
	public Map<MP,POINT> toMapMultiPointUnique(List<POINT> list)
	{
		Map<MP,POINT> map = new HashMap<MP,POINT>();
		for(POINT p : list)
		{
			map.put(p.getMultiPoint(),p);
			
		}
		return map;
	}
	public Map<DATA,POINT> toMapDataUnique(List<POINT> list)
	{
		Map<DATA,POINT> map = new HashMap<DATA,POINT>();
		for(POINT p : list)
		{
			map.put(p.getData(),p);
		}
		return map;
	}
}