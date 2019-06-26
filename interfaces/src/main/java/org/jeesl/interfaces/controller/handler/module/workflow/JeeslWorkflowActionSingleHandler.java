package org.jeesl.interfaces.controller.handler.module.workflow;

import java.util.List;

import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowActionSingleHandler<WA extends JeeslWorkflowAction<?,AB,AO,RE,RA>,
											AB extends JeeslWorkflowBot<AB,?,?,?>,
											AO extends EjbWithId,
											RE extends JeeslRevisionEntity<?,?,?,?,RA>,
											RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
											AW extends JeeslApprovalWorkflow<?,?,?>,
											WCS extends JeeslConstraint<?,?,?,?,?,?,?,?>>
{
	List<WCS> workflowPreconditions(JeeslWithWorkflow<?> entity, WA action);
	void perform(JeeslWithWorkflow<AW> entity, WA action) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException;
}