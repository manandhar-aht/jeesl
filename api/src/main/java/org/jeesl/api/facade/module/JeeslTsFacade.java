package org.jeesl.api.facade.module;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTsFacade <L extends UtilsLang,
								D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends JeeslTimeSeries<L,D,SCOPE,BRIDGE,INT>,
								TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								SOURCE extends EjbWithLangDescription<L,D>, 
								BRIDGE extends JeeslTsBridge<EC>,
								EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends JeeslTsData<L,D,TS,TRANSACTION,SAMPLE,WS>,
								SAMPLE extends JeeslTsSample, 
								USER extends EjbWithId, 
								WS extends UtilsStatus<WS,L,D>,
								QAF extends UtilsStatus<QAF,L,D>>
			extends UtilsFacade
{	
	List<SCOPE> findScopes(Class<SCOPE> cScope, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes);
	List<EC> findClasses(Class<EC> cClass, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleClasses);
	
	<T extends EjbWithId> BRIDGE fBridge(Class<BRIDGE> cBridge, EC entityClass, T ejb) throws UtilsNotFoundException;
	<T extends EjbWithId> BRIDGE fcBridge(Class<BRIDGE> cBridge, EC entityClass, T ejb) throws UtilsConstraintViolationException;
	
	List<TS> fTimeSeries(SCOPE scope, INT interval, EC entityClass);
	TS fTimeSeries(SCOPE scope, INT interval, BRIDGE bridge) throws UtilsNotFoundException;
	TS fcTimeSeries(SCOPE scope, INT interval, BRIDGE bridge) throws UtilsConstraintViolationException;
	
	List<DATA> fData(TRANSACTION transaction);
	List<DATA> fData(WS workspace, TS timeSeries);
	List<DATA> fData(WS workspace, TS timeSeries, Date from, Date to);
	
	List<TRANSACTION> fTransactions(List<USER> users, Date from, Date to);
	
	void deleteTransaction(TRANSACTION transaction) throws UtilsConstraintViolationException;
}