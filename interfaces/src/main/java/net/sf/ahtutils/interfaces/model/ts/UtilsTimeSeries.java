package net.sf.ahtutils.interfaces.model.ts;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsTimeSeries <L extends UtilsLang,
									D extends UtilsDescription,
									SCOPE extends UtilsTsScope<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									TS extends UtilsTimeSeries<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
									ENTITY extends EjbWithId,
									INT extends UtilsStatus<INT,L,D>,
									DATA extends UtilsTsData<L,D,SCOPE,UNIT,TS,ENTITY,INT,DATA>>
		extends EjbWithId
{
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	INT getInterval();
	void setInterval(INT interval);
	
	ENTITY getEntity();
	void setEntity(ENTITY entity);
}