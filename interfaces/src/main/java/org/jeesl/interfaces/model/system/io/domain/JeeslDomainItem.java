package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDomainItem <QUERY extends JeeslDomainQuery<?,?,?,?>,
									SET extends JeeslDomainSet<?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbWithPositionVisibleParent,EjbRemoveable
{
	public enum Attributes{itemSet,criteria}
	
	SET getItemSet();
	void setItemSet(SET itemSet);
	
	QUERY getQuery();
	void setQuery(QUERY query);
	
//	Boolean getTableHeader();
//	void setTableHeader(Boolean tableHeader);
}