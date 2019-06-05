package org.jeesl.interfaces.controller.processor;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.approval.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

public interface WorkflowRecipientResolver<ROLE extends JeeslSecurityRole<?,?,?,?,?,?,USER>,
										   AW extends JeeslApprovalWorkflow<?,?,?>,
										   USER extends JeeslUser<ROLE>>
							extends Serializable
{
	List<USER> getRecipients(JeeslWithWorkflow<AW> workflowOwner, ROLE role);
}