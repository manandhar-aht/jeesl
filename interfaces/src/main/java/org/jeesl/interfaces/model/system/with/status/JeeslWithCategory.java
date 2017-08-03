package org.jeesl.interfaces.model.system.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithCategory<L extends UtilsLang, D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>>
						extends EjbWithId
{
	public static String attributeCategory = "category";
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}