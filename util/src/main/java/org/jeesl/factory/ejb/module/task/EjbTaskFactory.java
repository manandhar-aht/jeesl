package org.jeesl.factory.ejb.module.task;

import net.sf.ahtutils.interfaces.model.issue.UtilsTask;
import net.sf.ahtutils.xml.issue.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTaskFactory<T extends UtilsTask<T>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTaskFactory.class);
	
	final Class<T> clTask;
	
    public EjbTaskFactory(final Class<T> clTask)
    {
        this.clTask = clTask;
    } 
    
    public static <T extends UtilsTask<T>> EjbTaskFactory<T> factory(final Class<T> clTask)
    {
        return new EjbTaskFactory<T>(clTask);
    }
    	
    public T build() {return build(null,null,null);}
    public T buildCode(String code) {return build(null,code,null);}
    public T buildCode(T parent,String code) {return build(parent,code,null);}
    public T build(T parent) {return build(parent,null,null);}
    
    public T build(T parent, Task task) {return build(parent, task.getCode(), task.getName());}
    
	public T build(T parent, String code, String name)
	{
		try
		{
			T t = clTask.newInstance();
			t.setParent(parent);
			t.setCode(code);
			t.setName(name);
		    return t;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		throw new RuntimeException("x");
    }
}