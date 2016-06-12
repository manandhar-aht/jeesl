package net.sf.ahtutils.jsf.handler.crud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.crud.EjbCrudWithParent;
import net.sf.ahtutils.interfaces.web.crud.CrudHandler1Bean;
import net.sf.ahtutils.interfaces.web.crud.CrudHandler2Bean;
import net.sf.ahtutils.interfaces.web.crud.CrudHandlerBean;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class UtilsCrudHandlerParent <T extends EjbCrudWithParent, P extends EjbWithId>
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsCrudHandlerParent.class);
	
	private CrudHandlerBean<T> bean;
	private CrudHandler1Bean<T> bean1;
	private CrudHandler2Bean<T> bean2;
	
	private UtilsFacade fUtils;
	
	private final Class<T> cT;
	
	private T prototype;
	
	public UtilsCrudHandlerParent(CrudHandlerBean<T> bean, UtilsFacade fUtils, Class<T> cT)
	{
		this(fUtils,cT);
		this.bean=bean;
	}
	
	public UtilsCrudHandlerParent(CrudHandler1Bean<T> bean1, UtilsFacade fUtils, Class<T> cT)
	{
		this(fUtils,cT);
		this.bean1=bean1;
	}
	
	public UtilsCrudHandlerParent(CrudHandler2Bean<T> bean2, UtilsFacade fUtils, Class<T> cT)
	{
		this(fUtils,cT);
		this.bean2=bean2;
	}
	
	private UtilsCrudHandlerParent(UtilsFacade fUtils, Class<T> cT)
	{
		this.fUtils=fUtils;
		this.cT=cT;
		
		try {prototype = cT.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
	}

	private List<T> list;
	public List<T> getList() {return list;}

	public void reloadList()
	{
		list = fUtils.allForParent(cT, prototype.resolveParentAttribute(), parent);
	}
	
	public void add()
	{
		logger.info(AbstractLogMessage.addEntity(cT));
		if(bean!=null){entity = bean.crudBuild(cT);}
		else if(bean1!=null){entity = bean1.crud1Build(cT);}
		else if(bean2!=null){entity = bean2.crud2Build(cT);}
		else {logger.warn("No Bean available!!");}
	}
	
	public void select()
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
		if(bean!=null){bean.crudNotifySelect(entity);}
		else if(bean1!=null){bean1.crud1Select();}
		else if(bean2!=null){bean2.crud2Select();}
		else {logger.warn("No Bean available!!");}
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(bean!=null){entity = bean.crudPreSave(entity);}
		else if(bean1!=null){entity = bean1.crud1Update(entity);}
		else if(bean2!=null){entity = bean2.crud2Update(entity);}
		entity = fUtils.save(entity);
		reloadList();
	}
	
	public void rm() throws UtilsConstraintViolationException
	{
		fUtils.rm(entity);
		entity=null;
		reloadList();
	}
	
	private T entity;
	public T getEntity() {return entity;}
	public void setEntity(T entity) {this.entity = entity;}
	
	private P parent;
	public P getParent() {return parent;}
	public void setParent(P parent) {this.parent = parent;}
}