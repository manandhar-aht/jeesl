package net.sf.ahtutils.interfaces.model.ts;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsTsData <L extends UtilsLang,
								D extends UtilsDescription,
								SCOPE extends UtilsTsScope<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends UtilsTimeSeries<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
								ENTITY extends EjbWithId,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends UtilsTsData<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>>
		extends EjbWithId, EjbWithRecord
{
	TS getTimeSeries();
	void setTimeSeries(TS timeSeries);
	
	Double getValue();
	void setValue(Double value);
}