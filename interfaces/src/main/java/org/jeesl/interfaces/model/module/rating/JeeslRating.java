package org.jeesl.interfaces.model.module.rating;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRating<L extends UtilsLang, D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								DOMAIN extends EjbWithId
>
			extends Serializable,EjbWithId,EjbSaveable,EjbWithParentAttributeResolver
{
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	int getRating();
	void setRating(int rating);
}