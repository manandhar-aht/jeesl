package org.jeesl.interfaces.facade;

import java.util.List;
import java.util.Map;

import org.jeesl.model.json.JsonFlatFigures;
import org.openfuxml.content.table.Table;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface JeeslIoDbFacade extends UtilsFacade
{
	String version();
	long count(Class<?> c);
	Map<Class<?>, Long> count(List<Class<?>> list);
	
	Table connections(String userName);
	JsonFlatFigures dbConnections(String dbName);
}