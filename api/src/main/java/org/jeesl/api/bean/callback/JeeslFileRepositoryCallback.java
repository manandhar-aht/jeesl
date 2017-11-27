package org.jeesl.api.bean.callback;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFileRepositoryCallback
{	
	void fileRepositoryContainerSaved(EjbWithId id) throws UtilsConstraintViolationException, UtilsLockingException;
}