package org.jeesl.interfaces.controller.handler.module.workflow;

import java.util.List;

import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowActionHandler<AA extends JeeslApprovalAction<?,AB,AO,RE,RA>,
											AB extends JeeslApprovalBot<AB,?,?,?>,
											AO extends EjbWithId,
											RE extends JeeslRevisionEntity<?,?,?,?,RA>,
											RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
											AW extends JeeslApprovalWorkflow<?,?,?>>
{
	void setDebugOnInfo(boolean debugOnInfo);
	<W extends JeeslWithWorkflow<AW>> void perform(JeeslWithWorkflow<AW> entity, List<AA> actions) throws UtilsConstraintViolationException, UtilsLockingException;
}