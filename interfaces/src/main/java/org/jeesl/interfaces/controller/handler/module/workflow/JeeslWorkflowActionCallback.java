package org.jeesl.interfaces.controller.handler.module.workflow;

import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public interface JeeslWorkflowActionCallback<WA extends JeeslWorkflowAction<?,?,?,?,?>>
{
	void workflowAbort(JeeslWithWorkflow<?> entity) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException;
	void workflowCallback(JeeslWithWorkflow<?> entity) throws UtilsConstraintViolationException, UtilsLockingException;
}