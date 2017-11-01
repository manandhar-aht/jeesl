package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeContainer <SET extends JeeslAttributeSet<?,?,?,?>, DATA extends JeeslAttributeData<?,?>>
		extends EjbWithId,EjbSaveable
{
	SET getSet();
	void setSet(SET set);
}