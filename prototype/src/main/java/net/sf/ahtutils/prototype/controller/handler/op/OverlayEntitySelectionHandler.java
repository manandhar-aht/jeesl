package net.sf.ahtutils.prototype.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.op.OpEntityBean;
import net.sf.ahtutils.interfaces.controller.handler.op.OpEntitySelectionHandler;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.prototype.web.mbean.admin.system.security.AbstractAdminSecurityViewBean;

public class OverlayEntitySelectionHandler <T extends EjbWithId>
	implements OpEntitySelectionHandler<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelectionHandler.class);
	public static final long serialVersionUID=1;

    private OpEntityBean<T> bean;

    private T op; @Override public T getOp() {return op;} @Override public void setOp(T opEntity) {this.op = opEntity;}
	private T tb; @Override public T getTb() {return tb;} @Override public void setTb(T tbEntity) {this.tb = tbEntity;}

	private List<T> opEntites; @Override public List<T> getOpList() {return opEntites;} @Override public void setOpList(List<T> opEntites) {this.opEntites = opEntites;}
	private List<T> tbEntites; @Override public List<T> getTbList() {return tbEntites;} @Override public void setTbList(List<T> tbEntites) {this.tbEntites = tbEntites;}

    public OverlayEntitySelectionHandler(OpEntityBean<T> bean)
    {
        this.bean=bean;
        tbEntites = new ArrayList<T>();
    }

    @Override public void selectListener() throws UtilsLockingException, UtilsConstraintViolationException
    {
        bean.addOpEntity(op);
        op = null;
    }

    @Override public void clearTable()
    {
    	tbEntites.clear();
    	tb = null;
    }

    @Override  public void addEntity(T item)
    {
        if(!tbEntites.contains(item)) {tbEntites.add(item);}
        tb=null;
    }

    @Override public void removeEntity()
    {
        if(tbEntites.contains(tb)) {tbEntites.remove(tb);}
        tb=null;
    }
    
	@Override public void selectTb() {}
	@Override public void rmTb() throws UtilsLockingException, UtilsConstraintViolationException
	{
		bean.rmOpEntity(tb);
		tb = null;
	}
}