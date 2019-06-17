package org.jeesl.interfaces.controller.handler.module.workflow;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public interface JeeslWorkflowActionCallback
{
	void workflowCallbackAfterActionsPerformed() throws UtilsConstraintViolationException, UtilsLockingException;
	void workflowCallback(String command) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException;
}