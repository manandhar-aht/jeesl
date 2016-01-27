package net.sf.ahtutils.interfaces.model.ts;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsTimeSeries <L extends UtilsLang,
									D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									INT extends UtilsStatus<INT,L,D>,
									TS extends UtilsTimeSeries<L,D,CAT,INT,TS,DATA>,
									DATA extends UtilsTsData<L,D,CAT,INT,TS,DATA>>
		extends EjbWithId
{

}