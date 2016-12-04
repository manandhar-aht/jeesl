package net.sf.ahtutils.interfaces.model.system.io;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
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
				EjbWithCode,EjbWithPositionVisible,EjbWithParentAttributeResolver,EjbWithPositionParent,
		EjbWithLang<L>,EjbWithDescription<D>
{	
	IOTT getType2();
	void setType2(IOTT type);
	
	IOTC getCategory2();
	void setCategory2(IOTC category);
}