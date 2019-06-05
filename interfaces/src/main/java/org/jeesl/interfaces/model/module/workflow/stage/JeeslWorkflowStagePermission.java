package org.jeesl.interfaces.model.module.workflow.stage;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowStagePermission <AS extends JeeslWorkflowStage<?,?,?,?>,
									APT extends JeeslWorkflowPermissionType<APT,?,?,?>,
									MR extends JeeslSecurityRole<?,?,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver
{
	public static enum Attributes{stage}
	
	AS getStage();
	void setStage(AS stage);
	
	APT getType();
	void setType(APT type);
	
	MR getRole();
	void setRole(MR role);
}