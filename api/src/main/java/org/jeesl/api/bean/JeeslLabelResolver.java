package org.jeesl.api.bean;

public interface JeeslLabelResolver
{	
	<E extends Enum<E>> String xpath(Class<?> c, E code);
}