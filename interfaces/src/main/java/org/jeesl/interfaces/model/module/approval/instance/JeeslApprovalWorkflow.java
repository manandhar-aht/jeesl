package org.jeesl.interfaces.model.module.approval.instance;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalWorkflow <AP extends JeeslApprovalProcess<?,?,?>
									
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver
{
	public static enum Attributes{process}
	
	AP getProcess();
	void setProcess(AP process);
}