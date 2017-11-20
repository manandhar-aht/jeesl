package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslFileMeta<L extends UtilsLang,D extends UtilsDescription,
								CONTAINER extends JeeslFileContainer<L,D,?>,
								TYPE extends UtilsStatus<TYPE,L,D>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithName,EjbWithSize
{
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	TYPE getType();
	void setType(TYPE type);
}