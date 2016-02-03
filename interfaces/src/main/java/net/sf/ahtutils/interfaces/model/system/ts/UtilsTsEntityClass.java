package net.sf.ahtutils.interfaces.model.system.ts;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsTsEntityClass <L extends UtilsLang,
										D extends UtilsDescription,
										CAT extends UtilsStatus<CAT,L,D>,
										SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
										ENTITY extends UtilsTsEntity<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
										EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
										INT extends UtilsStatus<INT,L,D>,
										DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
										WS extends UtilsStatus<WS,L,D>,
										QAF extends UtilsStatus<QAF,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,
				EjbWithLang<L>,EjbWithDescription<D>
{
	String getAttribute();
	void setAttribute(String attribute);
	
	String getXpath();
	void setXpath(String xpath);
}