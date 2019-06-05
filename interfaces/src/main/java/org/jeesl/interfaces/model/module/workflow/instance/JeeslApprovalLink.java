package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalLink <AW extends JeeslApprovalWorkflow<?,?,?>,
											RE extends JeeslRevisionEntity<?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithRefId
{
	public static enum Attributes{refId,workflow,entity}
	
	AW getWorkflow();
	void setWorkflow(AW workflow);
	
	RE getEntity();
	void setEntity(RE entity);
}