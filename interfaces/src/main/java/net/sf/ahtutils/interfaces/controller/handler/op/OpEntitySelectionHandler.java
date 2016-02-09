package net.sf.ahtutils.interfaces.controller.handler.op;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OpEntitySelectionHandler <T extends EjbWithId>
{
	public static final long serialVersionUID=1;

    public T getOp();
    public void setOp(T user);
    
    public T getTb();
    public void setTb(T user);

    public List<T> getOpList();
    public void setOpList(List<T> opEntites);

    public List<T> getTbList();
    public void setTbList(List<T> tbEntites);

    public void selectListener() throws UtilsLockingException, UtilsConstraintViolationException;

    public void clearTable();
    public void addEntity(T item);
    public void removeEntity();

    public void selectTb();
    public void rmTb() throws UtilsLockingException, UtilsConstraintViolationException;
}