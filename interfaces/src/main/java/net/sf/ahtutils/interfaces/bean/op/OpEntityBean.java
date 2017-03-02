package net.sf.ahtutils.interfaces.bean.op;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OpEntityBean
{
	void addOpEntity(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException;
	void rmOpEntity(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException;
}