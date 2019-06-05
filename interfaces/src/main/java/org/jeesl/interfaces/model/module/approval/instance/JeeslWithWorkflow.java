package org.jeesl.interfaces.model.module.approval.instance;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithWorkflow<AW extends JeeslApprovalWorkflow<?,?,?>> extends EjbWithId
{
	public static enum Attributes{workflow}
	
	//This is a marker interface for all domain classes which have a workflow correlation
}