package org.jeesl.interfaces.model.module.inventory.pc;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslInventorySample<L extends UtilsLang, D extends UtilsDescription,
										PC extends JeeslInventoryPc<L,D,PC,SAMPLE>,
										SAMPLE extends JeeslInventorySample<L,D,PC,SAMPLE>>
						extends EjbWithId, EjbSaveable, EjbWithRecord
{	
	
}