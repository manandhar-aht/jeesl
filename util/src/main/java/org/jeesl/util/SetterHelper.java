package org.jeesl.util;

import java.lang.reflect.Method;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * This utility can be used when a setter should be called without knowing a priori the exact type cast to be made
 */
public class SetterHelper {
	
	/**
	 * Uses reflection to set a property. The parameter will be casted as needed.
	 * @param propertyName The objects property, e.g. name or age
	 * @param targetObject The object for which the parameter should be set
	 * @param parameter The parameter, e.g. "Mr Smith" or "35"
	 */
	public static void set(String propertyName, Object targetObject, Object parameter)
	{
		try {
			Object[] arguments = new Object[1];
			Method setter = PropertyUtils.getPropertyDescriptor(targetObject, propertyName).getWriteMethod();
			arguments[0] = prepareParameter(parameter, setter.getParameterTypes()[0]);

			setter.invoke(targetObject, arguments);	
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Performs the type cast to match the required class
	 * Often, the exact class might not be the right class for the setter to be called (e.g. having a Double for setting an int setter)
	 * @param parameter The value
	 * @param targetClass The class accepted by the setter
	 * @return
	 */
	public static Object prepareParameter(Object parameter, Class targetClass)
	{
		Object correctParameter = parameter;
		String parameterClass = targetClass.getCanonicalName();
		// Needed to correct the Class of the general number
			if (parameterClass.equals("long"))
			{
				Number number = (Number) parameter;
				correctParameter = number.longValue();
			}

			// Needed to correct the Class of the general number
			else if (parameterClass.equals("int"))
			{
				if (parameter.getClass().equals(String.class))
				{
					String param = (String) parameter;
					correctParameter = Integer.parseInt(param);
				}
				else
				{
					Number number = (Number) parameter;
					correctParameter = number.intValue();
				}
			}

			// Needed to correct the Class of the general number
			else if (parameterClass.equals("java.lang.Integer"))
			{
				Number number = (Number) parameter;
				correctParameter = new Integer(number.intValue());
			}

							// Needed to correct the Class of the general number
			else if (parameterClass.equals("java.lang.Double"))
			{
				Number number = (Number) parameter;
				correctParameter = new Double(number.doubleValue());
			}

			// This is important if the String is a Number, Excel will format the cell to be a "general number"
			else if (parameterClass.equals("java.lang.String"))
			{
				if (parameter.getClass().getName().equals("java.lang.Double"))
				{
					Double n			= (Double) parameter;
					if (n % 1 == 0)
					{
						correctParameter	= "" +n.intValue();
					}
					else
					{
						correctParameter	= "" +n;
					}
				}
				else
				{
					correctParameter = parameter +"";
				}
			}

			// This is important if the Boolean is a Number or String
			else if (parameterClass.equals("java.lang.Boolean") || parameterClass.equals("boolean"))
			{
				if (parameter.getClass().equals(String.class))
				{
					String value = (String) parameter;
					if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("1"))
					{
						correctParameter = true;
					}
					else
					{
						correctParameter = false;
					}
				}
				if (parameter.getClass().equals(Number.class))
				{
					Number number = (Number) parameter;
					Boolean b     = true;
					if (number.intValue() == 0)
					{
						correctParameter = false;
					}
				}
			}
		return correctParameter;
	}
	
}
