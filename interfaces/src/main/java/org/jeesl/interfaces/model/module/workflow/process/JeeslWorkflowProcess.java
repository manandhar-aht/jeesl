package org.jeesl.interfaces.model.module.workflow.process;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.status.JeeslWithContext;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslWorkflowProcess <L extends UtilsLang, D extends UtilsDescription,
									WX extends JeeslWorkflowContext<WX,L,D,?>
									
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithCode,EjbWithPosition,
				JeeslWithContext<WX>,EjbWithLang<L>,EjbWithDescription<D>
{
	
}