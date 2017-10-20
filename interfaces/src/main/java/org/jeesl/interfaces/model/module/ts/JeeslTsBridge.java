package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTsBridge <EC extends JeeslTsEntityClass<?,?,?>>
		extends EjbWithId,EjbWithRefId
{
	public enum Attributes{entityClass}
	
	public EC getEntityClass();
	void setEntityClass(EC entityClass);
}