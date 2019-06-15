package org.jeesl.interfaces.controller.handler.module.workflow;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.Mail;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public interface JeeslWorkflowMessageHandler<ROLE extends JeeslSecurityRole<?,?,?,?,?,?,USER>,
											MT extends JeeslIoTemplate<?,?,?,?,MD,?>,
											MD extends JeeslIoTemplateDefinition<?,?,MT>,
											WF extends JeeslApprovalWorkflow<?,?,?>,
											USER extends JeeslUser<ROLE>>
							extends Serializable
{
	List<USER> getRecipients(JeeslWithWorkflow<WF> workflowOwner, ROLE role, WF workflow);
	EmailAddress buildEmail(USER user);
	String localeCode(USER user);
	void spool(Mail mail) throws UtilsConstraintViolationException, UtilsNotFoundException;
	
	List<MD> getDefinitions(MT template);
}