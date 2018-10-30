package org.jeesl.factory.ejb.system.io.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAttributeDataFactory<CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,
									OPTION extends JeeslAttributeOption<?,?,CRITERIA>,
									CONTAINER extends JeeslAttributeContainer<?,DATA>,
									DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAttributeDataFactory.class);
	
	private final Class<DATA> cData;
    
	public EjbAttributeDataFactory(final Class<DATA> cData)
	{       
        this.cData = cData;
	}
    
	public DATA build(CONTAINER container, CRITERIA criteria)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setCriteria(criteria);
			ejb.setContainer(container);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public List<DATA> copy(CONTAINER toContainer, List<DATA> list)
	{
		List<DATA> result = new ArrayList<DATA>();
		for(DATA d : list)
		{
			result.add(copy(toContainer,d));
		}
		return result;
	}
	
	public DATA copy(CONTAINER toContainer, DATA data)
	{
		DATA ejb = build(toContainer, data.getCriteria());
		ejb.setValueString(data.getValueString());
		ejb.setValueBoolean(data.getValueBoolean());
		ejb.setValueInteger(data.getValueInteger());
		ejb.setValueDouble(data.getValueDouble());
		ejb.setValueRecord(data.getValueRecord());
		ejb.setValueOption(data.getValueOption());
		
		List<OPTION> list = new ArrayList<OPTION>();
		list.addAll(data.getValueOptions());
		
		ejb.setValueOptions(list);
		return ejb;
	}
	
	public Map<CONTAINER,DATA> toMapSingleData(List<DATA> datas)
	{
		Map<CONTAINER,DATA> map = new HashMap<CONTAINER,DATA>();
		for(DATA d : datas) {map.put(d.getContainer(), d);}
		
		return map;
	}
}