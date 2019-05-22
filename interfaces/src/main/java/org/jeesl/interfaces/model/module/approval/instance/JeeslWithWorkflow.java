package org.jeesl.interfaces.model.module.approval.instance;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithWorkflow<AW extends JeeslApprovalWorkflow<?,?,?>> extends EjbWithId
{
	public static enum Attributes{workflow}
	
	AW getWorkflow();
	void setWorkflow(AW workflow);
}