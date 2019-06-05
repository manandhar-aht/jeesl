package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransition;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslApprovalActivity <AT extends JeeslApprovalTransition<?,?,?,?>,
										AW extends JeeslApprovalWorkflow<?,?,?>,
										USER extends JeeslUser<?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver,
				EjbWithRecord
{
	public static enum Attributes{workflow}
	
	AW getWorkflow();
	void setWorkflow(AW workflow);
	
	AT getTransition();
	void setTransition(AT transition);
	
	USER getUser();
	void setUser(USER user);
	
	String getRemark();
	void setRemark(String remark);
}