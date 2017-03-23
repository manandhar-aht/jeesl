package net.sf.ahtutils.test.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jeesl.model.ejb.system.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstReflection 
{
	final static Logger logger = LoggerFactory.getLogger(TstReflection.class);
	
	public static void main(String args[])
    {
       try
       {
          Class<?> c = Class.forName(Status.class.getName());
          
          Annotation a[] = c.getAnnotations();
          for (int i = 0; i < a.length; i++)
          {
              System.out.println(a[i].toString());
          }
          
          Method m[] = c.getDeclaredMethods();
          for (int i = 0; i < m.length; i++)
          {
        	  System.out.println(m[i].toString());
        	  Annotation ma[] = m[i].getAnnotations();
              for (int j = 0; j < ma.length; j++)
              {
                  System.out.println("   "+ma[j].toString());
              }
        	 
          }
          
          Field f[] = c.getDeclaredFields();
          for (int i = 0; i < f.length; i++)
          {
        	  Annotation fa[] = f[i].getAnnotations();
              for (int j = 0; j < fa.length; j++)
              {
                  System.out.println("   "+fa[j].toString());
              }
              System.out.println(f[i].toString());
          }

       }
       catch (Throwable e) {
          System.err.println(e);
       }
    }
}