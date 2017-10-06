package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractOpSelectionHandler <T extends EjbWithId> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;
	
    protected T op; @Override public T getOp() {return op;} @Override public void setOp(T opEntity) {this.op = opEntity;}
    protected T tb; @Override public T getTb() {return tb;} @Override public void setTb(T tbEntity) {this.tb = tbEntity;}

    protected List<T> opList; @Override public List<T> getOpList() {return opList;} @Override public void setOpList(List<T> list) {opList.clear(); opList.addAll(list);}
    protected List<T> tbList; @Override public List<T> getTbList() {return tbList;} @Override public void setTbList(List<T> list) {tbList.clear(); tbList.addAll(list);}
    
    protected OpEntityBean bean;

    protected boolean showName; public boolean isShowName() {return showName;}
	protected boolean showLang; public boolean isShowLang() {return showLang;}
    
    public AbstractOpSelectionHandler(OpEntityBean bean, List<T> opList)
    {
	    	this.bean=bean;
	    	this.opList=opList;
	    	showName=false;
	    	showLang=false;
	    	if(opList==null){opList = new ArrayList<T>();}
	    	tbList = new ArrayList<T>(); 
    }
    
    protected void reset(boolean rTb, boolean rOp)
    {
        if(rTb){tb=null;}
        if(rOp){op=null;}
    }
    
    @Override public void clearTable()
    {
	    	tbList.clear();
	    	tb = null;
    }
    
	@Override public void selectTb() {}
	
	@Override public void addEntity(T item) throws UtilsLockingException, UtilsConstraintViolationException
    {
	    	op = item;
	    	addEntity();
	}
	
	@SuppressWarnings({"unchecked" })
	public <C extends EjbWithCode, E extends Enum<E>> void addEntity(UtilsFacade fUtils, Class<C> c, E code)
    {
		try
		{
			addEntity((T)fUtils.fByCode(c, code));
		}
		catch (UtilsLockingException e) {e.printStackTrace();}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}

    @Override public void addEntity() throws UtilsLockingException, UtilsConstraintViolationException
    {
        if(op!=null && !tbList.contains(op))
        {
	        	tbList.add(op);
	        	bean.addOpEntity(op);
        }
        reset(true,true);
    }

    @Override public void removeEntity() throws UtilsLockingException, UtilsConstraintViolationException
    {
        if(tbList.contains(tb))
        {
        	tbList.remove(tb);
        	bean.rmOpEntity(tb);
        }
        reset(true,true);
    }
}