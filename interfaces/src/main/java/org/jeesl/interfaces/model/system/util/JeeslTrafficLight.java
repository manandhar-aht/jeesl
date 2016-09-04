package org.jeesl.interfaces.model.system.util;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.status.UtilsWithScope;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTrafficLight<L extends UtilsLang,
							D extends UtilsDescription,
							SCOPE extends UtilsStatus<SCOPE,L,D>>
			extends EjbWithId,
					EjbSaveable,EjbRemoveable,
					UtilsWithScope<L,D,SCOPE>,
					EjbWithLangDescription<L,D>
{
	double getThreshold();
	void setThreshold(double threshold);
	
	String getColorBackground();
	void setColorBackground(String colorBackground);
	
	String getColorText();
	void setColorText(String colorText);
}