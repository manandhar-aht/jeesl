package org.jeesl.factory.mc.ts;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsDataPointFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.DataSet;
import org.metachart.xml.chart.Ds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.DateUtil;

public class McDataSetFactory <SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
								MP extends JeeslTsMultiPoint<?,?,SCOPE,?>,
								TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
								BRIDGE extends JeeslTsBridge<EC>,
								EC extends JeeslTsEntityClass<?,?,?>,
								INT extends UtilsStatus<INT,?,?>,
								DATA extends JeeslTsData<TS,?,?,WS>,
								POINT extends JeeslTsDataPoint<DATA,MP>,
								WS extends UtilsStatus<WS,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(McDataSetFactory.class);
	
	private final JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,DATA,POINT,?,?,WS,?> fTs;
	
	private final TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,DATA,POINT,?,?,WS,?> fbTs;
	private final EjbTsDataPointFactory<MP,DATA,POINT> efPoint;
	
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC entityClass; public EC getEntityClass() {return entityClass;} public void setEntityClass(EC entityClass) {this.entityClass = entityClass;}
	private INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	private WS workspace; public WS getWorkspace() {return workspace;} public void setWorkspace(WS workspace) {this.workspace = workspace;}
	
	public McDataSetFactory(TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,DATA,POINT,?,?,WS,?> fbTs,
							   JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,DATA,POINT,?,?,WS,?> fTs)
	{
		this.fbTs=fbTs;
		this.fTs=fTs;
		
		efPoint = fbTs.ejbDataPoint();
	}
	
	public <E extends Enum<E>> void initScope(E scope) throws UtilsNotFoundException {this.scope = fTs.fByCode(fbTs.getClassScope(), scope);}
	public void initEntityClass(Class<?> c) throws UtilsNotFoundException {this.entityClass = fTs.fByCode(fbTs.getClassEntity(), c.getName());}
	public <E extends Enum<E>> void initInterval(E interval) throws UtilsNotFoundException {this.interval = fTs.fByCode(fbTs.getClassInterval(), interval);}
	public <E extends Enum<E>> void initWorkspace(E workspace) throws UtilsNotFoundException {this.workspace = fTs.fByCode(fbTs.getClassWorkspace(), workspace);}

	
	public DataSet build(List<DATA> datas)
	{
		DataSet ds = new DataSet();
		
		for(DATA data: datas)
		{
			Data cd = new Data();
			cd.setRecord(DateUtil.toXmlGc(data.getRecord()));
			if(data.getValue()!=null) {cd.setY(data.getValue());}
			ds.getData().add(cd);
		}
		return ds;	
	}
	
	public static DataSet build(TimeSeries timeSeries)
	{
		DataSet ds = new DataSet();
		
		for(org.jeesl.model.xml.module.ts.Data tsD: timeSeries.getData())
		{
			if (tsD.isSetValue())
			{
				Data cd = new Data();
				cd.setRecord(tsD.getRecord());

				cd.setY(tsD.getValue());
				ds.getData().add(cd);
			}
		}
		return ds;	
	}
	
	public <T extends EjbWithId> Ds multiPoint(String localeCode, T entity, Date from, Date to) throws UtilsNotFoundException
	{
		BRIDGE bridge = fTs.fBridge(fbTs.getClassBridge(),entityClass,entity);
		TS ts = fTs.fTimeSeries(scope,interval,bridge);
		
		List<MP> multiPoints = fTs.allForParent(fbTs.getClassMp(),scope);
		List<DATA> datas = fTs.fData(workspace,ts,from,to);
		List<POINT> points = fTs.fPoints(workspace,ts,from,to);
		Map<MP,List<POINT>> mapMp = efPoint.toMapMultiPoint(points);
		
		for(MP mp : mapMp.keySet())
		{
			
			List<POINT> l = mapMp.get(mp);
			logger.info(mp.toString() + " "+l.size());
		}
		
		logger.info("Data: "+datas.size()+" Points: "+points.size());
		
		Ds xml = new Ds();
		for(MP mp : multiPoints)
		{
			if(mp.getVisible() && mapMp.containsKey(mp))
			{
				Map<DATA,POINT> mapData = efPoint.toMapDataUnique(mapMp.get(mp));
				logger.info("MAP-data: "+mapData.size());
				Ds ds = new Ds();
				ds.setLabel(mp.getName().get(localeCode).getLang());
				for(DATA data : datas)
				{
					Data d = new Data();
					d.setRecord(DateUtil.toXmlGc(data.getRecord()));
					POINT p = mapData.get(data);
					logger.info("P: "+(p!=null) + " "+mapData.containsKey(data));
					
					if(p!=null) {d.setY(p.getValue());}
					ds.getData().add(d);
				}
				xml.getDs().add(ds);
			}
		}
		
		
		return xml;	
	}
}