package org.jeesl.interfaces.controller.handler.module.workflow;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface JeeslWorkflowActionCallback
{
	void callbackAfterActionsPerformed() throws UtilsConstraintViolationException, UtilsLockingException;
}