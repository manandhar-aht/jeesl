package net.sf.ahtutils.interfaces.controller.handler.op;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OpEntitySelectionHandler <T extends EjbWithId>
{
	public static final long serialVersionUID=1;

    public T getOp();
    public void setOp(T op);
    
    public T getTb();
    public void setTb(T tb);

    public List<T> getOpList();
    public void setOpList(List<T> opEntites);

    public List<T> getTbList();
    public void setTbList(List<T> tbEntites);

    public void clearTable();
    public void addEntity(T item) throws UtilsLockingException, UtilsConstraintViolationException;
    public void addEntity() throws UtilsLockingException, UtilsConstraintViolationException;
    public void removeEntity() throws UtilsLockingException, UtilsConstraintViolationException;

    public void selectTb();
}