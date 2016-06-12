package net.sf.ahtutils.jsf.handler.crud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.crud.EjbPositionCrudWithParent;
import net.sf.ahtutils.interfaces.web.crud.CrudHandlerBean;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class UtilsCrudHandlerPositionParent <T extends EjbPositionCrudWithParent, P extends EjbWithId>
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsCrudHandlerPositionParent.class);
	
	private CrudHandlerBean<T> bean;
	private UtilsFacade fUtils;
	
	private final Class<T> cT;
	
	private T prototype;
	
	public UtilsCrudHandlerPositionParent(CrudHandlerBean<T> bean, UtilsFacade fUtils, Class<T> cT)
	{
		this.bean=bean;
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
		list = fUtils.allOrderedParent(cT, "position", true, prototype.resolveParentAttribute(), parent);
	}
	
	public void add()
	{
		logger.info(AbstractLogMessage.addEntity(cT));
		entity = bean.crudBuild(cT);
	}
	
	public void select()
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
		bean.crudNotifySelect(entity);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException
	{
		entity = bean.crudPreSave(entity);
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