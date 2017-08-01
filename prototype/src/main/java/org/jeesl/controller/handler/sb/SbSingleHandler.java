package org.jeesl.controller.handler.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbSingleHandler <T extends EjbWithId> implements Serializable,SbSingleBean
{
	final static Logger logger = LoggerFactory.getLogger(SbSingleHandler.class);
	private static final long serialVersionUID = 1L;

	private final SbSingleBean bean; 
	private final Class<T> c;
	
	private List<T> list;public List<T> getList() {return list;} public void setList(List<T> list) {this.list = list;}
	
	private T selection; public T getSelection() {return selection;} public void setSelection(T selected) {this.selection = selected;}

	public SbSingleHandler(Class<T> c, SbSingleBean bean){this(c,new ArrayList<T>(),bean);}	
	public SbSingleHandler(Class<T> c, List<T> list, SbSingleBean bean)
	{
		this.c=c;
		this.bean=bean;
		update(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(c.isAssignableFrom(item.getClass()))
		{
			selection=(T)item;
			if(bean!=null){bean.selectSbSingle(item);}
		}
	}
	
	public void update(List<T> list)
	{
		this.list=list;
		
		if(selection!=null)
		{
			if(!list.contains(selection)){selection=null;}	
		}
		if(selection==null && list!=null && !list.isEmpty())
		{
			selection=list.get(0);
		}
	}
	
	public void clear()
	{
		list.clear();
		selection = null;
	}
	
	public boolean getHasMore(){return list.size()>1;}
	public boolean isSelected(){return selection!=null;}
	
	public void selectDefault()
	{
		selection=null;
		if(list!=null && !list.isEmpty()){selection = list.get(0);}
	}
}