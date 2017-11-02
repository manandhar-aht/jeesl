package org.jeesl.controller.handler.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbSingleHandler <T extends EjbWithId> implements Serializable,SbSingleBean
{
	final static Logger logger = LoggerFactory.getLogger(SbSingleHandler.class);
	private static final long serialVersionUID = 1L;

	private final SbSingleBean bean; 
	private final Class<T> c;
	
	private List<T> list;public List<T> getList() {return list;} public void setList(List<T> list) {this.list = list;}
	
	private T selection; public T getSelection() {return selection;} public void setSelection(T selected) {this.selection = selected;}
	private T previous;
	
	public SbSingleHandler(Class<T> c, SbSingleBean bean){this(c,new ArrayList<T>(),bean);}	
	public SbSingleHandler(Class<T> c, List<T> list, SbSingleBean bean)
	{
		this.c=c;
		this.bean=bean;
		update(list);
		try
		{
			previous = c.newInstance();
			previous.setId(-1);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(item==null)
		{
			previous.setId(-1);
			selection = null;
			if(bean!=null){bean.selectSbSingle(null);}
		}
		else if(c.isAssignableFrom(item.getClass()))
		{
			if(selection!=null) {previous.setId(selection.getId());}
			selection=(T)item;
			StringBuilder sb = new StringBuilder();
			sb.append(this.getClass().getSimpleName());
			sb.append(" selected with:").append(selection.toString());
			sb.append(" Previous:").append(previous.getId());
			logger.trace(sb.toString());
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
	
	@SuppressWarnings("unchecked")
	public <E extends Enum<E>, S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> void add(UtilsFacade fUtils, Class<S> c, E code)
	{
		if(list==null) {list = new ArrayList<T>();}
		try
		{
			S status = fUtils.fByCode(c,code);
			list.add((T)status);
		}
		catch (UtilsNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		list.clear();
		selection = null;
	}
	
	public boolean getHasMore(){return list.size()>1;}
	public boolean isSelected(){return selection!=null;}
	public boolean getTwiceSelected() {return previous.equals(selection);}
	
	public void selectDefault()
	{
		selection=null;
		if(list!=null && !list.isEmpty()){selection = list.get(0);}
	}
	
	public void silentCallback()
	{
		try
		{
			if (list.isEmpty())
			{
				//selectSbSingle(null);
			}
			else
			{
				if(list.contains(selection)) {selectSbSingle(selection);}
				else{selectSbSingle(list.get(0));}	
			}
		}
		catch (UtilsLockingException e) {}
		catch (UtilsConstraintViolationException e) {}
	}
}