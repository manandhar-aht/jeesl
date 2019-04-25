package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTsBridge <EC extends JeeslTsEntityClass<?,?,?>>
					extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbWithRefId,EjbWithParentAttributeResolver
{
	enum Attributes{entityClass}
	
	EC getEntityClass();
	void setEntityClass(EC entityClass);
}