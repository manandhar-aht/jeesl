package org.jeesl.interfaces.model.system.io.mail.template;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoTemplateDefinition<D extends UtilsDescription,
								TYPE extends UtilsStatus<TYPE,?,D>,
								TEMPLATE extends JeeslIoTemplate<?,D,?,?,?,?>
								>
		extends Serializable,EjbPersistable,
				EjbWithId,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver,
				EjbWithDescription<D>
{	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	TYPE getType();
	void setType(TYPE type);
}