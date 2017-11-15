package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslIoCmsVisiblity
		extends Serializable,EjbWithId,
				EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithVisible
{	
		
}