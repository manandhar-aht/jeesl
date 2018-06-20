package org.jeesl.api.bean;

public interface JeeslLabelResolver
{	
	String entity(String localeCode, Class<?> c); 
	<E extends Enum<E>> String xpath(Class<?> c, E code);
}