package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeItem <CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,SET extends JeeslAttributeSet<?,?,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbWithPositionVisibleParent,EjbRemoveable
{
	public enum Attributes{itemSet,criteria}
	
	SET getItemSet();
	void setItemSet(SET itemSet);
	
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);
	
	Boolean getTableHeader();
	void setTableHeader(Boolean tableHeader);
}