package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeItem <SET extends JeeslAttributeSet<?,?,?,?>,
									CRITERIA extends JeeslAttributeCriteria<?,?,?,?>>
		extends EjbWithId,EjbWithPositionVisible
{
	SET getSet();
	void setSet(SET set);
	
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);
}