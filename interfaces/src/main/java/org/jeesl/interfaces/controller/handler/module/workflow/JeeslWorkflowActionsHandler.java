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

public interface JeeslWorkflowActionsHandler<WA extends JeeslWorkflowAction<?,AB,AO,RE,RA>,
											AB extends JeeslWorkflowBot<AB,?,?,?>,
											AO extends EjbWithId,
											RE extends JeeslRevisionEntity<?,?,?,?,RA>,
											RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
											AW extends JeeslApprovalWorkflow<?,?,?>,
											WCS extends JeeslConstraint<?,?,?,?,?,?,?,?>>
{
	void setDebugOnInfo(boolean debugOnInfo);
	
	void checkPreconditions(List<WCS> constraints, JeeslWithWorkflow<?> entity, List<WA> actions);
	<W extends JeeslWithWorkflow<AW>> void abort(JeeslWithWorkflow<AW> entity);
	<W extends JeeslWithWorkflow<AW>> JeeslWithWorkflow<AW> perform(JeeslWithWorkflow<AW> entity, List<WA> actions) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException;
}