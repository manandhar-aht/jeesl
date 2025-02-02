package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.api.rest.module.ts.JeeslTsRestExport;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsRestService <L extends UtilsLang,
							D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
							ST extends UtilsStatus<ST,L,D>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
							TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
							TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER,?>,
							SOURCE extends EjbWithLangDescription<L,D>, 
							BRIDGE extends JeeslTsBridge<EC>,
							EC extends JeeslTsEntityClass<L,D,CAT>,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
							POINT extends JeeslTsDataPoint<DATA,MP>,
							SAMPLE extends JeeslTsSample, 
							USER extends EjbWithId, 
							WS extends UtilsStatus<WS,L,D>,
							QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslTsRestExport
{
	final static Logger logger = LoggerFactory.getLogger(TsRestService.class);
	
	private final JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs;
	
	private final Class<UNIT> cUnit;
	
	private TsRestService(JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs,
							final Class<L> cL, final Class<D> cD, final Class<UNIT> cUnit)
	{
		super(fTs,cL,cD);
		this.fTs=fTs;
		
		this.cUnit=cUnit;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
					ST extends UtilsStatus<ST,L,D>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
					TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
					TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER,?>,
					SOURCE extends EjbWithLangDescription<L,D>, 
					BRIDGE extends JeeslTsBridge<EC>,
					EC extends JeeslTsEntityClass<L,D,CAT>,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
					SAMPLE extends JeeslTsSample,
					POINT extends JeeslTsDataPoint<DATA,MP>,
					USER extends EjbWithId, 
					WS extends UtilsStatus<WS,L,D>,
					QAF extends UtilsStatus<QAF,L,D>>
			TsRestService<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF>
			factory(JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs, final Class<L> cL, final Class<D> cD, final Class<UNIT> cUnit)
	{
		return new TsRestService<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF>(fTs,cL,cD,cUnit);
	}
	
	@Override public Container exportTsUnit() {return xfContainer.build(fTs.allOrderedPosition(cUnit));}
	@Override public Container exportTsUnitSi() {return xfContainer.build(fTs.allOrderedPosition(cUnit));}

//	@Override public DataUpdate importCalendarType(Container container){return importStatus(cType,container,null);}

}