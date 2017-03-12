package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslTsEntityClass <L extends UtilsLang,
										D extends UtilsDescription,
										CAT extends UtilsStatus<CAT,L,D>,
										SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
										BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
										EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
										INT extends UtilsStatus<INT,L,D>,
										DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
										WS extends UtilsStatus<WS,L,D>,
										QAF extends UtilsStatus<QAF,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,
				EjbWithPositionVisibleParent,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	CAT getCategory();
	void setCategory(CAT category);
	
	String getAttribute();
	void setAttribute(String attribute);
	
	String getXpath();
	void setXpath(String xpath);
}