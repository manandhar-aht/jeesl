package org.jeesl.interfaces.model.system.news;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidFromUntil;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSystemNews<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
								USER extends EjbWithId>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithValidFromUntil,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	USER getAuthor();
	void setAuthor(USER user);
}