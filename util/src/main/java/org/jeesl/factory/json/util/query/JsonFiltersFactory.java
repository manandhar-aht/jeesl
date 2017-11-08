package org.jeesl.factory.json.util.query;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.util.query.JsonFilter;
import org.jeesl.model.json.util.query.JsonFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonFiltersFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonFiltersFactory.class);
	
	public static <T extends EjbWithId> JsonFilters build(Class<T> c, JsonFilter.Type type, List<T> list) 
	{
		JsonFilters json = new JsonFilters();
		json.setFqcn(c.getName());
		json.setType(type.toString());
		json.setIds(EjbIdFactory.toLongs(list));
		return json;
	}
}