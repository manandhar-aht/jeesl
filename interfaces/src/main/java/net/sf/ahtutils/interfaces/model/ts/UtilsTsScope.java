package net.sf.ahtutils.interfaces.model.ts;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsTsScope <L extends UtilsLang,
									D extends UtilsDescription,
									SCOPE extends UtilsTsScope<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									TS extends UtilsTimeSeries<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
									ENTITY extends EjbWithId,
									INT extends UtilsStatus<INT,L,D>,
									DATA extends UtilsTsData<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{
	String getCode();
	void setCode(String code);
	
	UNIT getUnit();
	void setUnit(UNIT unit);
}