package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.op.OpEntityBean;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractOpSelectionHandler <T extends EjbWithId> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

    protected T op; @Override public T getOp() {return op;} @Override public void setOp(T opEntity) {this.op = opEntity;}
    protected T tb; @Override public T getTb() {return tb;} @Override public void setTb(T tbEntity) {this.tb = tbEntity;}

    protected List<T> opEntites; @Override public List<T> getOpList() {return opEntites;} @Override public void setOpList(List<T> opEntites) {this.opEntites = opEntites;}
    protected List<T> tbEntites; @Override public List<T> getTbList() {return tbEntites;} @Override public void setTbList(List<T> tbEntites) {this.tbEntites = tbEntites;}
    
    protected OpEntityBean bean;
    
    public AbstractOpSelectionHandler(OpEntityBean bean)
    {
        this(bean,new ArrayList<T>());
    }
    
    public AbstractOpSelectionHandler(OpEntityBean bean, List<T> opEntites)
    {
    	this.bean=bean;
    	this.opEntites=opEntites;
    }
    
    protected void reset(boolean rTb, boolean rOp)
    {
        if(rTb){tb=null;}
        if(rOp){op=null;}
    }
    
    @Override public void clearTable()
    {
    	tbEntites.clear();
    	tb = null;
    }
    
	@Override public void selectTb() {}
	
   @Override public void addEntity(T item) throws UtilsLockingException, UtilsConstraintViolationException
    {
    	op = item;
    	addEntity();
    }

    @Override public void addEntity() throws UtilsLockingException, UtilsConstraintViolationException
    {
        if(op!=null && !tbEntites.contains(op))
        {
        	tbEntites.add(op);
        	bean.addOpEntity(op);
        }
        reset(true,true);
    }

    @Override public void removeEntity() throws UtilsLockingException, UtilsConstraintViolationException
    {
        if(tbEntites.contains(tb))
        {
        	tbEntites.remove(tb);
        	bean.rmOpEntity(tb);
        }
        reset(true,true);
    }
}