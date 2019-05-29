package org.jeesl.interfaces.model.module.approval;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStage;

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

public interface JeeslApprovalTransition <L extends UtilsLang, D extends UtilsDescription,
									S extends JeeslApprovalStage<L,D,?,?>,
									ATT extends JeeslApprovalTransitionType<ATT,L,D,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Attributes{source,destination}
	
	ATT getType();
	void setType(ATT type);
	
	S getSource();
	void setSource(S source);
	
	S getDestination();
	void setDestination(S destination);
}