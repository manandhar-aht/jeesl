package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFileMeta<CONTAINER extends JeeslFileContainer<?,?>,
								TYPE extends UtilsStatus<TYPE,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithName,EjbWithSize,EjbWithRecord
{
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	TYPE getType();
	void setType(TYPE type);
}