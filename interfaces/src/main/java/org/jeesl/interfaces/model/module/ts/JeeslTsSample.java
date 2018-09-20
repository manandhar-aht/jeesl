package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsSample 
		extends EjbWithId,EjbSaveable,EjbWithRecord
{
	enum Attributes{record}
}