package org.jeesl.jsf.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentAttributeProcessor
{
	final static Logger logger = LoggerFactory.getLogger(ComponentAttributeProcessor.class);
	private static enum Attribute {scope,style}
		
	public static void defaultStyle(StringBuilder sb, Map<String,Object> map)
	{
		if (map.containsKey(Attribute.style.toString()))
		{
			sb.append(map.get(Attribute.style.toString()).toString());
		}
	}
}