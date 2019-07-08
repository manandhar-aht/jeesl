package org.jeesl.interfaces.model.module.workflow.transition;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
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
									S extends JeeslWorkflowStage<L,D,?,?,?>,
									ATT extends JeeslWorkflowTransitionType<ATT,L,D,?>,
									SR extends JeeslSecurityRole<?,?,?,?,?,?,?>,
									G extends JeeslGraphic<L,D,?,?,?>
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
	
	SR getRole();
	void setRole(SR role);
	
	Boolean getScreenSignature();
	void setScreenSignature(Boolean screenSignature);
	
	Boolean getRemarkMandatory();
	void setRemarkMandatory(Boolean remarkMandatory);
	
	Boolean getFileUpload();
	void setFileUpload(Boolean fileUpload);
}