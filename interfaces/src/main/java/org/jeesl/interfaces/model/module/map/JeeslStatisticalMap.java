package org.jeesl.interfaces.model.module.map;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslStatisticalMap<L extends UtilsLang, D extends UtilsDescription>
						extends Serializable,EjbSaveable,EjbRemoveable,
								EjbWithId,EjbWithPosition,EjbWithCode,
								EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{}
	
	
}