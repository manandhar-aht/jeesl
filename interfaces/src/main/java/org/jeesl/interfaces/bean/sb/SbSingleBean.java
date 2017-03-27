package org.jeesl.interfaces.bean.sb;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface SbSingleBean
{
	void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException;
}