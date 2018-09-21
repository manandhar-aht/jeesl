package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.op.OpEntitySelectionHandler;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.mc.ts.McTsViewerFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.model.xml.module.ts.Data;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.jeesl.util.comparator.xml.ts.TsDataComparator;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.controller.report.UtilsXlsDefinitionResolver;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractAdminTsImportManualBean<L extends UtilsLang, D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
											ST extends UtilsStatus<ST,L,D>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
											TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
											TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER>,
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
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsImportManualBean.class);

	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<EC> classes; public List<EC> getClasses() {return classes;}
	private List<INT> intervals; public List<INT> getIntervals() {return intervals;}
	private List<WS> workspaces; public List<WS> getWorkspaces() {return workspaces;}
	private List<SOURCE> sources; public List<SOURCE> getSources() {return sources;}

	private final List<EjbWithId> entities; public List<EjbWithId> getEntities() {return entities;}
	private Map<EjbWithId,String> mapLabels; public Map<EjbWithId,String> getMapLabels() {return mapLabels;}
	private EjbWithId entity; public EjbWithId getEntity() {return entity;} public void setEntity(EjbWithId entity) {this.entity = entity;}
	private EjbTsDataFactory<TS,TRANSACTION, DATA,WS> efData;

	protected CAT category;public CAT getCategory() {return category;}public void setCategory(CAT category) {this.category = category;}
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC clas; public EC getClas() {return clas;} public void setClas(EC clas) {this.clas = clas;}
	private INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	protected WS workspace; public WS getWorkspace() {return workspace;} public void setWorkspace(WS workspace) {this.workspace = workspace;}
	protected USER transactionUser;
	private TRANSACTION transaction; public TRANSACTION getTransaction() {return transaction;} public void setTransaction(TRANSACTION transaction) {this.transaction = transaction;}
	private TS ts;
	
	private TimeSeries timeSeries; public TimeSeries getTimeSeries() {return timeSeries;} public void setTimeSeries(TimeSeries timeSeries) {this.timeSeries = timeSeries;}
	private final Map<TS,EjbWithId> mapTsEntity; public Map<TS,EjbWithId> getMapTsEntity() {return mapTsEntity;}


	private DataSet ds; public DataSet getDs() {return ds;} public void setDs(DataSet ds) {this.ds = ds;}

	protected UtilsXlsDefinitionResolver xlsResolver;

	private Comparator<Data> cTsData;

	public AbstractAdminTsImportManualBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fbTs)
	{
		super(fbTs);
		entities = new ArrayList<EjbWithId>();
		mapTsEntity = new HashMap<TS,EjbWithId>();
		efData = fbTs.data();
	}

	protected void initSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs, JeeslFacesMessageBean bMessage, UtilsXlsDefinitionResolver xlsResolver)
	{
		super.initTsSuper(langs,fTs,bMessage);
		this.xlsResolver=xlsResolver;
		cTsData = TsDataComparator.factory(TsDataComparator.Type.date);
		sources = fTs.all(fbTs.getClassSource());
	}

	protected void initLists()
	{
		workspaces = fTs.all(fbTs.getClassWorkspace());
		category = null; if(categories.size()>0){category = categories.get(0);}
		changeCategory();
	}

	public void changeCategory()
	{
		scope=null;
		clas=null;
		interval=null;
		if(category!=null)
		{
			category = fTs.find(fbTs.getClassCategory(), category);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(category));}
			scopes = fTs.allOrderedPositionVisibleParent(fbTs.getClassScope(), category);
			if(scopes.size()>0){scope=scopes.get(0);}
			logger.info(category.toString());

			changeScope();
		}
	}

	private void changeScope()
	{
		clas=null;
		interval=null;
		if(scope!=null)
		{
			scope = fTs.find(fbTs.getClassScope(), scope);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(scope));}

			classes = scope.getClasses();
			if(classes.size()>0){clas=classes.get(0);}

			intervals = scope.getIntervals();
			if(intervals.size()>0){interval=intervals.get(0);}
			logger.info(scope.toString());
			changeClass();
			changeInterval();

		}
	}

	private void changeClass()
	{
		if(clas!=null)
		{
			clas = fTs.find(fbTs.getClassEntity(), clas);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(clas));}
			changeInterval();
		}
	}

	public void changeInterval()
	{
		if(interval!=null)
		{
			interval = fTs.find(fbTs.getClassInterval(), interval);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(interval));}
			if(intervals.size()>0){interval=intervals.get(0);}
			reloadBridges();
		}
	}

	protected BRIDGE bridge; public BRIDGE getBridge() {return bridge;}

	private void reloadBridges()
	{
		entities.clear();
		try
		{
			mapTsEntity.clear();
			Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(clas.getCode()).asSubclass(EjbWithId.class);
			entities.addAll(fTs.all(c));

			if(entities.size() > 0) {bridge = fTs.fcBridge(fbTs.getClassBridge(), clas, entities.get(0));}
		}
		catch (ClassNotFoundException e){e.printStackTrace();} catch(UtilsConstraintViolationException e) { e.printStackTrace(); }
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(EjbWithId.class,entities));}

	}

	private List<DATA> lData; public List<DATA> getlData() { return lData; }
	private DATA data; public DATA getData() { return data; } public void setData(DATA data) { this.data = data; }

	public void selectEntity() throws UtilsConstraintViolationException
	{
		logger.info("Selected: "+ entity.toString());
		
		//FIND The Bridge/Timeseries
		// ts = fTs.fc ..
//		lData = fTs.fData(workspace, tsh.getOpList().get(0));

		ts = fTs.fcTimeSeries(scope, interval,bridge);
		lData = fTs.fData(workspace, ts);

		McTsViewerFactory<TS,DATA> f = new McTsViewerFactory<TS,DATA>();
		ds=f.build(lData);
		JaxbUtil.info(ds);
	}

	public void addData()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTs.getClassData()));}
		transaction = fbTs.transaction().build(transactionUser, fTs.all(fbTs.getClassSource()).get(0));
		transaction.setRecord(new Date());
		
		//Tkae the TS
		data = efData.build(workspace, ts,null, null, null);
	}

	private Date date; public Date getDate() { return date; } public void setDate(Date date) { this.date = date; }
	private Double value; public Double getValue() { return value; } public void setValue(Double value) { this.value = value; }

	public void saveData() throws UtilsConstraintViolationException, UtilsLockingException
	{
//		if(date == null | value == null) { bMessage.errorText();
		transaction = fTs.save(transaction);
		data.setTransaction(transaction);
		data.setRecord(date); data.setValue(value);
		logger.info(AbstractLogMessage.saveEntity(data));
		fTs.save(data);
	}

	public void cancel() {
		data = null;
		transaction = null;
	}
}