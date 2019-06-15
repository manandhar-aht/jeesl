package org.jeesl.interfaces.model.module.workflow.transition;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslWorkflowTransition <L extends UtilsLang, D extends UtilsDescription,
									S extends JeeslWorkflowStage<L,D,?,?>,
									ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
									SR extends JeeslSecurityRole<?,?,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver,
				EjbWithLang<L>,EjbWithDescription<D>,
				EjbWithVisible
{
	public static enum Attributes{source,destination}
	
	ATT getType();
	void setType(ATT type);
	
	S getSource();
	void setSource(S source);
	
	S getDestination();
	void setDestination(S destination);
	
	Boolean getScreenSignature();
	void setScreenSignature(Boolean screenSignature);
	
	SR getRole();
	void setRole(SR role);
}