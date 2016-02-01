package net.sf.ahtutils.interfaces.controller.audit;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionPageFlow<T extends EjbWithId,P extends EjbWithId>
{		
	public void pageFlowPrimarySelect(T revision);
	public void pageFlowParentSelect(P parent);
	public void pageFlowPrimaryCancel();
	public void pageFlowPrimarySave(T revision);
	public void pageFlowPrimaryAdd();
	public void pageFlowActionInOther();
}