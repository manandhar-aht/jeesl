package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslFileStorage<L extends UtilsLang,D extends UtilsDescription,
									ENGINE extends UtilsStatus<ENGINE,L,D>>
		extends Serializable,EjbWithId,
			EjbSaveable,EjbRemoveable,
			EjbWithLang<L>,EjbWithDescription<D>
				
{	
	ENGINE getEngine();
	void setEngine(ENGINE engines);
	
	String getJson();
	void setJson(String json);
}