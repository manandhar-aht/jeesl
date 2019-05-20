package org.jeesl.interfaces.model.module.approval.instance;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithWorkflow<AW extends JeeslApprovalWorkflow<?>> extends EjbWithId
{
	AW getWorkflow();
	void setWorkflow(AW workflow);
}
