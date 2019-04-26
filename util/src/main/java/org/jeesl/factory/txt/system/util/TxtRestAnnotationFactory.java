package org.jeesl.factory.txt.system.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jeesl.interfaces.qualifier.RestDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtRestAnnotationFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtRestAnnotationFactory.class);
    
    public static String code(Class<?> c, String methodName)
    {
    	StringBuffer sb = new StringBuffer();
    	Method[] methods = c.getMethods();
    	for(Method m : methods)
    	{
    		if(m.getName().equals(methodName))
    		{
    			Annotation a = m.getAnnotation(RestDescription.class);
    			if(a!=null)
    			{
    				RestDescription rest = (RestDescription)a;
    				sb.append(rest.label());
    			}
    			
    		}
    	}
    	return sb.toString();
    }
}