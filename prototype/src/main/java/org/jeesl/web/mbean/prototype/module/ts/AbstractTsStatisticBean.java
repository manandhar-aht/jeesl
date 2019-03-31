package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.controller.handler.tuple.JsonTuple1Handler;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.model.json.module.ts.JsonTsStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.io.JsonUtil;

public class AbstractTsStatisticBean <L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
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
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractTsStatisticBean.class);

	protected final SbSingleHandler<EC> sbhClass; public SbSingleHandler<EC> getSbhClass() {return sbhClass;}
	private final JsonTuple1Handler<TS> th; public JsonTuple1Handler<TS> getTh() {return th;}

	protected String jsonStream; public String getJsonStream() {return jsonStream;}
	
	public AbstractTsStatisticBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fbTs)
	{
		super(fbTs);
		sbhClass = new SbSingleHandler<EC>(fbTs.getClassEntity(),this);
		th = new JsonTuple1Handler<TS>(fbTs.getClassTs());
	}
	
	protected void postConstructSummary(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs, JeeslFacesMessageBean bMessage)
	{
		super.initTsSuper(bTranslation.getLangKeys().toArray(new String[bTranslation.getLangKeys().size()]),fTs,bMessage);
		sbhClass.update(fTs.all(fbTs.getClassEntity()));
		reloadBridges();
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		reloadBridges();
	}
	
	public void cancelScope(){reset(true,true);}
	public void cancelMultiPoint(){reset(false,true);}
	public void reset(boolean rScope, boolean rMultiPoint)
	{
		
	}
	
	
	private void reloadBridges()
	{
		List<TS> series = fTs.all(fbTs.getClassTs());
		th.init(fTs.tpCountRecordsByTs(series));
		
		
		List<JsonTsStatistic> list = new ArrayList<JsonTsStatistic>();
		for(TS ts : series)
		{
			JsonTsStatistic json = new JsonTsStatistic();
			if(th.contains(ts)){json.setCount(Long.valueOf(th.getMap1().get(ts).getCount()).intValue());}
			else {json.setCount(0);}
			json.setCategory(ts.getScope().getCategory().getName().get("en").getLang());
			json.setScope(ts.getScope().getName().get("en").getLang());
			json.setEntity(ts.getBridge().getEntityClass().getName().get("en").getLang());
			json.setInterval(ts.getInterval().getName().get("en").getLang());
			list.add(json);
		}
		jsonStream = "";

		try {jsonStream = JsonUtil.toString(list);}
		catch (JsonProcessingException e) {e.printStackTrace();}
//		logger.info("Stram: "+jsonStream);
	}
}