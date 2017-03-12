package org.jeesl.api.facade.module;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTsFacade <L extends UtilsLang,
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
			extends UtilsFacade
{	
	List<SCOPE> findScopes(Class<SCOPE> cScope, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes);
	List<EC> findClasses(Class<EC> cClass, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleClasses);
	
	BRIDGE fBridge(Class<BRIDGE> cBridge, EC entityClass, long refId) throws UtilsNotFoundException;
	BRIDGE fcBridge(Class<BRIDGE> cBridge, EC entityClass, long refId) throws UtilsConstraintViolationException;
	
	TS fTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsNotFoundException;
	TS fcTimeSeries(Class<TS> cTs, SCOPE scope, INT interval, BRIDGE bridge) throws UtilsConstraintViolationException;
	
	List<DATA> fData(WS workspace, TS timeSeries);
	List<DATA> fData(WS workspace, TS timeSeries, Date from, Date to);
}