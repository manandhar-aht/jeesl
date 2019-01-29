package org.jeesl.api.handler;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OpEntitySelection <T extends EjbWithId>
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
    public void addEntity(T item) throws UtilsLockingException, UtilsConstraintViolationException, UtilsNotFoundException;
    public void addEntity() throws UtilsLockingException, UtilsConstraintViolationException, UtilsNotFoundException;
    public void removeEntity() throws UtilsLockingException, UtilsConstraintViolationException, UtilsNotFoundException;

    public void selectTb();
}