package org.jeesl.api.facade.core;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslProtectedFacade extends UtilsFacade
{	
	<T extends EjbWithId> T saveProtected(T o) throws UtilsConstraintViolationException,UtilsLockingException;
	<T extends Object> void rmProtected(T o) throws UtilsConstraintViolationException;
}