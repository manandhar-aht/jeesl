package org.jeesl.interfaces.model.module.workflow.action;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateType;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWorkflowCommunication <WT extends JeeslWorkflowTransition<?,?,?,?,?>,
											MT extends JeeslIoTemplate<?,?,?,?,?,?>,
											MC extends JeeslTemplateType<?,?,MC,?>,
											SR extends JeeslSecurityRole<?,?,?,?,?,?,?>,
											RE extends JeeslRevisionEntity<?,?,?,?,?>
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver
				
{
	public static enum Attributes{transition}
	
	WT getTransition();
	void setTransition(WT transition);
	
	MT getTemplate();
	void setTemplate(MT template);
	
	SR getRole();
	void setRole(SR role);
	
	RE getScope();
	void setScope(RE scope);
}