package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslApprovalActivity <AT extends JeeslWorkflowTransition<?,?,?,?,?>,
										WF extends JeeslApprovalWorkflow<?,?,?>,
										FRC extends JeeslFileContainer<?,?>,
										USER extends JeeslUser<?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver,
				EjbWithRecord,JeeslWithFileRepositoryContainer<FRC>
{
	public static enum Attributes{workflow}
	
	WF getWorkflow();
	void setWorkflow(WF workflow);
	
	AT getTransition();
	void setTransition(AT transition);
	
	USER getUser();
	void setUser(USER user);
	
	String getRemark();
	void setRemark(String remark);
	
	String getScreenSignature();
	void setScreenSignature(String screenSignature);
}