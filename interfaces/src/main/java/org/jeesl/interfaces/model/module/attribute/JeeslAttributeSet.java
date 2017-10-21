package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeSet <L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									ITEM extends JeeslAttributeItem<?,?>
									>
		extends EjbWithId
{
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}