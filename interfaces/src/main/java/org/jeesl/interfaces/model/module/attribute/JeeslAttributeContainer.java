package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeContainer <SET extends JeeslAttributeSet<?,?,?,?>, DATA extends JeeslAttributeData<?,?>>
		extends EjbWithId
{
	SET getSet();
	void setSet(SET set);
}