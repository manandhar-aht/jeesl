package org.jeesl.interfaces.controller.handler.module.workflow;

import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public interface JeeslWorkflowActionCallback<WA extends JeeslWorkflowAction<?,?,?,?,?>,
											WC extends JeeslConstraint<?,?,?,?,?,?,?,?>>
{
	void workflowAbort(JeeslWithWorkflow<?> entity) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException;
	void workflowCallback(JeeslWithWorkflow<?> entity) throws UtilsConstraintViolationException, UtilsLockingException;
	void workflowCallback(JeeslWithWorkflow<?> entity, WA action) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException;
//	List<WC> workflowPreconditions(JeeslWithWorkflow<?> entity, WA action);
}