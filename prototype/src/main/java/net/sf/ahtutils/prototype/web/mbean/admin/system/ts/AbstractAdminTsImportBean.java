package net.sf.ahtutils.prototype.web.mbean.admin.system.ts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.xml.module.ts.XmlDataFactory;
import org.jeesl.factory.xml.module.ts.XmlTimeSeriesFactory;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.model.xml.module.ts.Data;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.jeesl.util.comparator.xml.ts.TsDataComparator;
import org.joda.time.DateTime;
import org.metachart.xml.chart.DataSet;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.xml.mc.XmlMcDataSetFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.controller.report.UtilsXlsDefinitionResolver;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;
import net.sf.ahtutils.report.revert.excel.importers.ExcelSimpleSerializableImporter;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsImportBean <L extends UtilsLang, D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>, 
											BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											USER extends EjbWithId, 
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsImportBean.class);
	
	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<EC> classes; public List<EC> getClasses() {return classes;}
	private List<INT> intervals; public List<INT> getIntervals() {return intervals;}
	private List<WS> workspaces; public List<WS> getWorkspaces() {return workspaces;}
	
	private List<EjbWithId> entities; public List<EjbWithId> getEntities() {return entities;}
	private Map<EjbWithId,String> mapLabels; public Map<EjbWithId,String> getMapLabels() {return mapLabels;}
	private EjbWithId entity; public EjbWithId getEntity() {return entity;} public void setEntity(EjbWithId entity) {this.entity = entity;}
	
	private CAT category;public CAT getCategory() {return category;}public void setCategory(CAT category) {this.category = category;}
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC clas; public EC getClas() {return clas;} public void setClas(EC clas) {this.clas = clas;}
	private INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	private WS workspace; public WS getWorkspace() {return workspace;} public void setWorkspace(WS workspace) {this.workspace = workspace;}
	protected USER transactionUser;
	
	private TimeSeries timeSeries; public TimeSeries getTimeSeries() {return timeSeries;} public void setTimeSeries(TimeSeries timeSeries) {this.timeSeries = timeSeries;}
	private DataSet chartDs; public DataSet getChartDs(){return chartDs;}
	
	protected UtilsXlsDefinitionResolver xlsResolver;
	protected File importRoot;
	
	private Comparator<Data> cTsData;
	
	protected void initSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> fTs, FacesMessageBean bMessage, UtilsXlsDefinitionResolver xlsResolver, final Class<L> cLang, final Class<D> cDescription, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<TS> cTs, Class<BRIDGE> cBridge,Class<EC> cEc, Class<INT> cInt, Class<DATA> cData, Class<WS> cWs)
	{
		super.initTsSuper(langs,fTs,bMessage,cLang,cDescription,cCategory,cScope,cUnit,cTs,cBridge,cEc,cInt,cData,cWs);
		this.xlsResolver=xlsResolver;
		
		cTsData = TsDataComparator.factory(TsDataComparator.Type.date);
	}
	
	protected void initLists()
	{
		workspaces = fTs.all(cWs);
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
			category = fTs.find(cCategory, category);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(category));}
			scopes = fTs.allOrderedPositionVisibleParent(cScope, category);
			if(scopes.size()>0){scope=scopes.get(0);}
			changeScope();
		}
	}
	
	public void changeScope()
	{
		clas=null;
		interval=null;
		if(scope!=null)
		{
			scope = fTs.find(cScope, scope);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(scope));}
			
			classes = scope.getClasses();
			if(classes.size()>0){clas=classes.get(0);}
			
			intervals = scope.getIntervals();
			if(intervals.size()>0){interval=intervals.get(0);}
		}
	}
	
	public void changeClass()
	{
		if(clas!=null)
		{
			clas = fTs.find(cEc, clas);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(clas));}
		}
	}
	
	public void changeInterval()
	{
		if(interval!=null)
		{
			interval = fTs.find(cInt, interval);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(interval));}
		}
	}
	
	/**
	 * Import Excel time series to XML objects. 
	 * Excel file is stored locally, then loaded into a Apache POI object.
	 * Then AHTUtils ExcelImporter system is configured (what information is to be put where) and used to import Excel data linewise to XML object representation.
	 * @param event The PrimeFaces FileUpload event that contains the uploaded data and meta information
	 * @throws java.io.FileNotFoundException
	 */	
	public void uploadData(FileUploadEvent event) throws FileNotFoundException, IOException, ClassNotFoundException, Exception
	{
		// Store the uploaded data locally (is overwritten without asking for files with the same name) 
		// and save the filename for use in log messages when saving to database
		String filename = event.getFile().getFileName();
		File f = new File(importRoot,filename);
		FileOutputStream out = new FileOutputStream(f);
		IOUtils.copy(event.getFile().getInputstream(), out);
		
		// Create a new TimeSeries
		setTimeSeries(XmlTimeSeriesFactory.build());
		
		// Get a new Resolver that gives you the XlsWorkbook by asking the reports.xml registry for the file
		
		// Instantiate and configure the importer
		// ATTENTION: Do not use the primary key option here (would cause bad results)
		ExcelSimpleSerializableImporter<Data,ImportStrategy> statusImporter = ExcelSimpleSerializableImporter.factory(xlsResolver, "TimeSeries", f.getAbsolutePath());
		statusImporter.selectFirstSheet();
		statusImporter.setFacade(fTs);
		statusImporter.getTempPropertyStore().put("createEntityForUnknown", true);
		statusImporter.getTempPropertyStore().put("lookup", false);
		  
		Map<Data,ArrayList<String>> data  = statusImporter.execute(true);
		
		if(debugOnInfo){logger.info("Loaded " +data.size() +" time series data entries to be saved in the database.");}
		timeSeries.getData().addAll(data.keySet());
		Collections.sort(timeSeries.getData(), cTsData);
		entity=null;
		preview();
	}
	
	public void random()
	{
		DateTime dt = new DateTime(new Date());
		Random rnd = new Random();
		
		timeSeries = XmlTimeSeriesFactory.build();
		for(int i=0;i<5;i++)
		{
			timeSeries.getData().add(XmlDataFactory.build(dt.plusDays(i).toDate(), rnd.nextInt(10)*rnd.nextDouble()));
		}
		
		entity=null;
		preview();
	}
	
	@SuppressWarnings("unchecked")
	private void preview()
	{
		entities = new ArrayList<EjbWithId>();
		mapLabels = new HashMap<EjbWithId,String>();
		
		chartDs = XmlMcDataSetFactory.build(timeSeries);
		try
		{
			Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(clas.getCode()).asSubclass(EjbWithId.class);
			
			for(EjbWithId e : fTs.all(c))
			{
				entities.add(e);
				JXPathContext ctx = JXPathContext.newContext(e);
				mapLabels.put(e, (String)ctx.getValue(clas.getXpath()));
			}
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public void selectEntity()
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
	}
	
	public void importData()
	{
		workspace = fTs.find(cWs, workspace);
		logger.info("Import Data to "+workspace);
		
		try
		{
			BRIDGE bridge = fTs.fcBridge(cBridge, clas, entity.getId());
			TS ts = fTs.fcTimeSeries(cTs, scope, interval, bridge);
			logger.info("Using TS "+ts.toString());
			
			TRANSACTION transaction = efTransaction.build(transactionUser);
			
			List<DATA> datas = new ArrayList<DATA>();
			for(Data data : timeSeries.getData())
			{
				datas.add(efData.build(workspace, ts, data));
			}
			fTs.save(datas);
			
			entities=null;
			entity=null;
			timeSeries=null;
			chartDs=null;
		}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		catch (UtilsLockingException e) {e.printStackTrace();}
	}
}