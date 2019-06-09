package org.jeesl.interfaces.model.system.io.ssi;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiSystem
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithCode
{	
	
}