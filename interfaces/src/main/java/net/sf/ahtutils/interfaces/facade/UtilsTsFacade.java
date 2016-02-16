package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsBridge;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;

public interface UtilsTsFacade <L extends UtilsLang,
								D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								BRIDGE extends UtilsTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								WS extends UtilsStatus<WS,L,D>,
								QAF extends UtilsStatus<QAF,L,D>>
			extends UtilsFacade
{	
	List<SCOPE> findScopes(Class<SCOPE> cScope, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes);
	List<EC> findClasses(Class<EC> cClass, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleClasses);
	
	BRIDGE fBridge(Class<BRIDGE> cBridge, EC entityClass, long refId) throws UtilsNotFoundException;
	BRIDGE fcBridge(Class<BRIDGE> cBridge, EC entityClass, long refId) throws UtilsConstraintViolationException;
	
	TS fTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsNotFoundException;
	TS fcTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsConstraintViolationException;
	
	List<DATA> fData(Class<DATA> cData, WS workspace, TS timeSeries);
}