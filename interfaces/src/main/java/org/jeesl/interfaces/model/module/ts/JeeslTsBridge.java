package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTsBridge <EC extends JeeslTsEntityClass<?,?,?>>
		extends EjbWithId,EjbWithRefId
{
	enum Attributes{entityClass}
	
	EC getEntityClass();
	void setEntityClass(EC entityClass);
}