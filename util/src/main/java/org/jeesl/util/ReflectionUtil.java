package org.jeesl.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {

	final static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * Inspect if an Object has some given method
	 * @param object     The parent Object (e.g. Person)
	 * @param methodName The name of the method (e.g. setName)
	 * @return Is method there? (e.g. true)
	 */
	public static Boolean hasMethod(Object object, String methodName)
	{
		Method[] methods  = object.getClass().getMethods();
		Boolean hasMethod = false;
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				hasMethod = true;
			}
		}
		return hasMethod;
	}

	/**
	 * Intended to get the class of the first argument to be passed to a method
	 * @param object     The parent Object (e.g. Person)
	 * @param methodName The name of the method (e.g. setName)
	 * @return Class of the first parameter (e.g. java.lang.String)
	 */
	 public static Class<?> getParameterClass(Object object, String methodName)
	 {
		logger.trace("Getting parameter class for " +methodName +" in class " +object.getClass().getSimpleName());

		// Now find the correct method
		Method[] methods = object.getClass().getMethods();
		Class<?> parameter  = null;
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				parameter = method.getParameterTypes()[0];
			}
		}
		return parameter;
	 }

 	/**
	 * First skeleton for traversing through .-separated methods (get or create/set)
	 * NOT READY YET!
	 * @param parent      The parent Object
	 * @param expression  .-separated fields (eg. person.name.lastName)
	 * @return The parent Object now including the value to be set
	 */
	public static Object resolveExpression(Object parent, String expression) throws Exception
	{
		if(parent != null)
		{
//			 logger.trace("Resolving " +expression + " on " +parent.toString());
			if (expression.contains("."))
			{
				int elements = expression.split("\\.").length;
				logger.trace("Still resolving " +elements);
				String pathToProperty = expression.substring(0, expression.indexOf("."));
				logger.trace("Path to Property " +pathToProperty);
				String getter         = "get" +(pathToProperty.substring(0, 1).toUpperCase() + pathToProperty.substring(1));
				logger.trace("Getter: " +getter);
				Object nextObject = simpleInvokeMethod(getter,
						new Object[] { },
						parent.getClass(),
						parent);
				return resolveExpression(nextObject, expression.substring(expression.indexOf(".")+1, expression.length()));
			}
			else
			{
				String methodName = "get" +(expression.substring(0, 1).toUpperCase() + expression.substring(1));
				logger.trace("Requesting value by invoking " +methodName + "()");
				return simpleInvokeMethod(methodName,
						new Object[] { },
						parent.getClass(),
						parent);
			}
		}
		else
		{
			return null;
		}
	}

	 /**
		 * Invoke a method on a given object
		 * @param methodName   Method to be invoked (e.g. "setId")
		 * @param parameters   Array of parameters to be used as they appear in the method (e.g. [Long id])
		 * @param targetClass  Targeted class
		 * @param target       The actual object that holds the method to be invoked
		 * @return The returned object (e.g. the long value of the ID) or NULL if no method with @param methodName was found
		 */
	 public static Object simpleInvokeMethod(String   methodName,
				Object[] parameters,
				Class<?> targetClass,
				Object   target)        throws Exception
	    {
		logger.trace("Invoking " +methodName);

		// Now find the correct method
                logger.trace("Searching for methods of class " +targetClass.getName());
		Method[] methods = targetClass.getMethods();
		Method m         = null;
		for (Method method : methods)
		{
            logger.trace("Found method " +method.getName());
            if (method.getName().equals(methodName))
            {
                    m = method;
            }
		}
		if (m != null) {
            if (Modifier.isPrivate(m.getModifiers()))
            {
                m.setAccessible(true);
            }
                return m.invoke(target, parameters);
        }
        else
        {
            return null;
        }
	}

}
