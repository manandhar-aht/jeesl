package org.jeesl.interfaces.model.system;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslFeature <L extends UtilsLang, D extends UtilsDescription>
		extends EjbSaveable,EjbWithLang<L>,EjbWithDescription<D>,EjbWithCode,EjbWithPositionVisible
{
	
}