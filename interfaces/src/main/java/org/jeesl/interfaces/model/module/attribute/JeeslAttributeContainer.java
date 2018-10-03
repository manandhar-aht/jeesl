package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeContainer <SET extends JeeslAttributeSet<?,?,?,?>,
											DATA extends JeeslAttributeData<?,?,?>>
		extends Serializable,EjbRemoveable,EjbWithId,EjbSaveable
{
	public static enum Attributes{datas};
	
	SET getSet();
	void setSet(SET set);
}