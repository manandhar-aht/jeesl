package net.sf.ahtutils.prototype.controller.handler.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class TableMultiSelectionHandler <T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(TableMultiSelectionHandler.class);

	protected List<T> entities; public List<T> getEntities() {return entities;}
	private T entity; public T getEntity() {return entity;} public void setEntity(T entity) {this.entity = entity;}
	private Map<T,Boolean> map; public Map<T,Boolean> getMap(){return map;}

	public TableMultiSelectionHandler()
	{
		map = new ConcurrentHashMap<T,Boolean>();
	}
	
	public void preSelect(List<T> types)
	{
		map.clear();
		for(T type : types)
		{
			map.put(type,true);
		}
	}

	public void select()
	{
		
		if (map.containsKey(entity)) {map.put(entity, !map.get(entity));}
		else {map.put(entity, true);}
		logger.info(AbstractLogMessage.selectEntity(entity)+": "+map.get(entity));
	}

	public List<T> toSelected()
	{
		List<T> types = new ArrayList<T>();
		for(T type : map.keySet())
		{
			if(map.get(type))
			{
				types.add(type);
			}
		}
		return types;
	}
}
