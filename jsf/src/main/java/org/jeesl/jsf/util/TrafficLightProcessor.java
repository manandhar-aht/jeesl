package org.jeesl.jsf.util;

import java.util.List;

import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TrafficLightProcessor
{
	final static Logger logger = LoggerFactory.getLogger(TrafficLightProcessor.class);
	
	public static <L extends UtilsLang,D extends UtilsDescription, LIGHT extends JeeslTrafficLight<L,D,SCOPE>,SCOPE extends UtilsStatus<SCOPE,L,D>>
		LIGHT findLight(List<LIGHT> lights, double value)
	{
		if(logger.isTraceEnabled()){logger.info("Loaded " + lights.size() + " Traffic Light Definitions from Application Scoped Bean.");}
		
		LIGHT result = null;
		for (LIGHT light : lights)
		{
			if(logger.isTraceEnabled()){logger.info("Comparing " +value +">=" +light.getThreshold());}
			if (value >= light.getThreshold())
			{
				result = light;
			}
		}
		return result;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, LIGHT extends JeeslTrafficLight<L,D,SCOPE>,SCOPE extends UtilsStatus<SCOPE,L,D>>
		String findScope(Object scopeAttribute)
	{
		String scope = "";
		if(logger.isTraceEnabled()){logger.info("scope has class " +scopeAttribute.getClass().toString());}
		
		if (scopeAttribute instanceof UtilsStatus)
		{
			@SuppressWarnings("unchecked")
			SCOPE scopeObj = (SCOPE) scopeAttribute;
			if(logger.isTraceEnabled()){logger.info("Scope is given as object" +scopeObj.getCode());}
			scope = scopeObj.getCode();
		}
		else
		{
			if(logger.isTraceEnabled()){logger.info("Scope is not given as Object. To String results in " +scopeAttribute.toString());}
			scope = scopeAttribute.toString();
		}
		return scope;
	}
}