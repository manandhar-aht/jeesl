package net.sf.ahtutils.interfaces.facade;

import java.util.List;
import java.util.Map;

import org.openfuxml.content.table.Table;

public interface UtilsDbFacade extends UtilsFacade
{
	long count(Class<?> c);
	Map<Class<?>, Long> count(List<Class<?>> list);
	
	Table connections(String userName);
}