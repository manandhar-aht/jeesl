package org.jeesl.interfaces.model.module.log;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLog <L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<L,D,LOG,ITEM,IMPACT,SCOPE>,
								ITEM extends JeeslLogItem<L,D,LOG,ITEM,IMPACT,SCOPE>,
								IMPACT extends UtilsStatus<IMPACT,L,D>,
								SCOPE extends UtilsStatus<SCOPE,L,D>
								>
		extends EjbWithId
{
	
}