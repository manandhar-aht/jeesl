package net.sf.ahtutils.jsf.handler.crud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.crud.EjbCrud;
import net.sf.ahtutils.interfaces.web.crud.CrudHandlerBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class UtilsCrudHandlerSimple <T extends EjbCrud>
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsCrudHandlerSimple.class);
	
	private UtilsFacade fUtils;
	private CrudHandlerBean<T> bean;
	
	private final Class<T> cT;
	
	private List<T> list; public List<T> getList() {return list;}
	
	public UtilsCrudHandlerSimple(CrudHandlerBean<T> bean, UtilsFacade fUtils, Class<T> cT)
	{
		this(fUtils,cT);
		this.bean=bean;
	}
	
	private UtilsCrudHandlerSimple(UtilsFacade fUtils, Class<T> cT)
	{
		this.fUtils=fUtils;
		this.cT=cT;
	}

	public void reloadList()
	{
		list = fUtils.all(cT);
	}
	
	public void add()
	{
		logger.info(AbstractLogMessage.addEntity(cT));
		if(bean!=null){entity = bean.crudBuild(cT);}
		else {logger.warn("No Bean available!!");}
	}
	
	public void select()
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
		if(bean!=null){bean.crudNotifySelect(entity);}
		else {logger.warn("No Bean available!!");}
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(bean!=null){entity = bean.crudPreSave(entity);}
		entity = fUtils.save(entity);
		reloadList();
	}
	
	public void rm()
	{
		try
		{
			fUtils.rm(entity);
			entity=null;
			reloadList();
		}
		catch (UtilsConstraintViolationException e)
		{
			bean.crudRmConstraintViolation(cT);
		}
	}
	
	private T entity;
	public T getEntity() {return entity;}
	public void setEntity(T entity) {this.entity = entity;}
}