package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDomainPath<L extends UtilsLang, D extends UtilsDescription,
										QUERY extends JeeslDomainQuery<L,D,?,?>,
										ENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
										ATTRIBUTE extends JeeslRevisionAttribute<L,D,?,?,?>
										>
			extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithPositionParent
{
	public enum Attributes{query}
	
	QUERY getQuery();
	void setQuery(QUERY query);
	
	ENTITY getEntity();
	void setEntity(ENTITY entity);
	
	ATTRIBUTE getAttribute();
	void setAttribute(ATTRIBUTE attribute);
}