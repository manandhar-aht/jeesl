package org.jeesl.interfaces.model.module.approval;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalCommunication <T extends JeeslApprovalTransition<?,?,?>
//											MT extends JeeslIoTemplate<?,?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver
				
{
	public static enum Attributes{transition}
	
	T getTransition();
	void setTransition(T transition);
	
//	MT getTemplate();
//	void setTemplate(MT template);
}