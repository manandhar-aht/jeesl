package org.jeesl.interfaces.bean.sb;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface SbToggleBean
{
	void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException;
}