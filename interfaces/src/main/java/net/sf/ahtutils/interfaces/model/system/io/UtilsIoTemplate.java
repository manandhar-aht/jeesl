package net.sf.ahtutils.interfaces.model.system.io;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsIoTemplate<L extends UtilsLang,D extends UtilsDescription,
									IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
									IOTT extends UtilsStatus<IOTT,L,D>,
									IOTC extends UtilsStatus<IOTC,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithParent,EjbWithPositionParent,
		EjbWithLang<L>,EjbWithDescription<D>
{	
	IOTT getType();
	void setType(IOTT type);
	
	IOTC getCategory();
	void setCategory(IOTC category);
}