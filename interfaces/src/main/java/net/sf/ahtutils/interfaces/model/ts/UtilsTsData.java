package net.sf.ahtutils.interfaces.model.ts;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsTsData <L extends UtilsLang,
								D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
								ENTITY extends EjbWithId,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
								WS extends UtilsStatus<WS,L,D>>
		extends EjbWithId, EjbWithRecord
{
	TS getTimeSeries();
	void setTimeSeries(TS timeSeries);
	
	WS getWorkspace();
	void setWorkspace(WS workspace);
	
	Double getValue();
	void setValue(Double value);
}