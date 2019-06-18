package org.jeesl.interfaces.model.with.status;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithCategory<CATEGORY extends UtilsStatus<CATEGORY,?,?>>
						extends EjbWithId
{
	public static String attributeCategory = "category";
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}