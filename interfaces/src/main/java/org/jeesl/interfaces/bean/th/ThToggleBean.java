package org.jeesl.interfaces.bean.th;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface ThToggleBean
{
	void toggled(ThToggleFilter filter, Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException;
}