package org.jeesl.web.rest.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportCellFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportTemplateFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportsFactory;
import org.jeesl.factory.xml.system.io.report.XmlTemplateFactory;
import org.jeesl.factory.xml.system.io.report.XmlTemplatesFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestExport;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestImport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportTemplateComparator;
import org.jeesl.util.query.xml.ReportQuery;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.report.Cell;
import net.sf.ahtutils.xml.report.ColumnGroup;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.Row;
import net.sf.ahtutils.xml.report.Rows;
import net.sf.ahtutils.xml.report.Template;
import net.sf.ahtutils.xml.report.Templates;
import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsWorkbook;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class IoReportRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
									WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
									RT extends UtilsStatus<RT,L,D>,
									ENTITY extends EjbWithId,
									ATTRIBUTE extends EjbWithId,
									FILLING extends UtilsStatus<FILLING,L,D>,
									TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
					implements JeeslIoReportRestExport,JeeslIoReportRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoReportRestService.class);
	
	private JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<REPORT> cReport;
	private final Class<IMPLEMENTATION> cImplementation;
	private final Class<SHEET> cSheet;
	private final Class<GROUP> cGroup;
	private final Class<COLUMN> cColumn;
	private final Class<ROW> cRow;
	private final Class<TEMPLATE> cTemplate;
	private final Class<CELL> cCell;
	private final Class<CDT> cDataType;
	private final Class<CW> cColumWidth;
	private final Class<RT> cRt;
	private final Class<FILLING> cFilling;
	private final Class<TRANSFORMATION> cTransformation;

	private XmlContainerFactory xfContainer;
	private XmlReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xfReport;
	private XmlTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE> xfTemplate;
	
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efReport;
	private EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efWorkbook;
	private EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efSheet;
	private EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efGroup;
	private EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efColumn;
	private EjbIoReportRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efRow;
	private EjbIoReportTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efTemplate;
	private EjbIoReportCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efCell;
		
	private Comparator<REPORT> comparatorReport;
	private Comparator<TEMPLATE> comparatorTemplate;
	
	private IoReportRestService(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CELL> cCell,final Class<CDT> cDataType, final Class<CW> cColumWidth, final Class<RT> cRt, final Class<FILLING> cFilling, final Class<TRANSFORMATION> cTransformation,final Class<IMPLEMENTATION> cImplementation)
	{
		this.fReport=fReport;
		this.cL=cL;
		this.cD=cD;
		
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cImplementation=cImplementation;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
		this.cColumn=cColumn;
		this.cRow=cRow;
		this.cTemplate=cTemplate;
		this.cCell=cCell;
		this.cDataType=cDataType;
		this.cColumWidth=cColumWidth;
		this.cRt=cRt;
		
		this.cFilling=cFilling;
		this.cTransformation=cTransformation;
		
		xfContainer = new XmlContainerFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		xfReport = new XmlReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(ReportQuery.get(ReportQuery.Key.exReport));
		xfTemplate = new XmlTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>(ReportQuery.exTemplate());
		
		ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> ffReport = ReportFactoryFactory.factory(cL,cD,cReport,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cDataType,cColumWidth);
		efReport = ffReport.report();
		efWorkbook = ffReport.workbook();
		efSheet = ffReport.sheet();
		efGroup = ffReport.group();
		efColumn = ffReport.column();
		efRow = ffReport.row();
		efTemplate = ffReport.template();
		efCell = ffReport.cell();
		
		comparatorReport = new IoReportComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportComparator.Type.position);
		comparatorTemplate = new IoReportTemplateComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportTemplateComparator.Type.position);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
					IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
					WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
					SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
					GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
					COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
					ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
					TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
					RT extends UtilsStatus<RT,L,D>,
					ENTITY extends EjbWithId,
					ATTRIBUTE extends EjbWithId,
					FILLING extends UtilsStatus<FILLING,L,D>,
					TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
					>
	IoReportRestService<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>
			factory(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CELL> cCell, final Class<CDT> cDataType, final Class<CW> cColumWidth, final Class<RT> cRt, final Class<IMPLEMENTATION> cImplementation, final Class<FILLING> cFilling,final Class<TRANSFORMATION> cTransformation)
	{
		return new IoReportRestService<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(fReport,cL,cD,cCategory,cReport,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cDataType,cColumWidth,cRt,cFilling,cTransformation,cImplementation);
	}
	
	@Override public Container exportSystemIoReportCategories() {return xfContainer.build(fReport.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoReportSettingFilling() {return xfContainer.build(fReport.allOrderedPosition(cFilling));}
	@Override public Container exportSystemIoReportSettingTransformation() {return xfContainer.build(fReport.allOrderedPosition(cTransformation));}
	@Override public Container exportSystemIoReportSettingImplementation() {return xfContainer.build(fReport.allOrderedPosition(cImplementation));}
	@Override public Container exportSystemIoReportRowType() {return xfContainer.build(fReport.allOrderedPosition(cRt));}
	@Override public Container exportSystemIoReportColumnWidth() {return xfContainer.build(fReport.allOrderedPosition(cColumWidth));}

	@Override public Reports exportSystemIoReports()
	{
		Reports reports = XmlReportsFactory.build();
		List<REPORT> list = fReport.all(cReport);
		Collections.sort(list,comparatorReport);
		for(REPORT report : list)
		{
			reports.getReport().add(xfReport.build(report));
		}
		return reports;
	}
	
	@Override public Templates exportSystemIoReportTemplates()
	{
		Templates templates = XmlTemplatesFactory.build();
		List<TEMPLATE> list = fReport.all(cTemplate);
		Collections.sort(list,comparatorTemplate);
		for(TEMPLATE template : list)
		{
			templates.getTemplate().add(xfTemplate.build(template));
		}
		return templates;
	}
	
	@Override public DataUpdate importSystemIoReportCategories(Container categories){return importStatus(cCategory,cL,cD,categories,null);}
	@Override public DataUpdate importSystemIoReportSettingFilling(Container types){return importStatus(cFilling,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportSettingTransformation(Container types){return importStatus(cTransformation,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportSettingImplementation(Container types){return importStatus(cImplementation,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportRowType(Container types){return importStatus(cRt,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportColumnWidth(Container types){return importStatus(cColumWidth,cL,cD,types,null);}
	
	@Override public DataUpdate importSystemIoReportTemplates(Templates templates)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cReport.getName(),"DB Import"));
		
		JeeslDbCodeEjbUpdater<TEMPLATE> dbUpdaterTemplate = JeeslDbCodeEjbUpdater.createFactory(cTemplate);
		dbUpdaterTemplate.dbEjbs(fReport);
		
		for(Template xTemplate : templates.getTemplate())
		{
			try
			{
				TEMPLATE eTemplate = importSystemIoReportTemplate(xTemplate);
				dbUpdaterTemplate.handled(eTemplate);
				dut.success();
			}
			catch (UtilsNotFoundException e) {dut.fail(e, true);}
			catch (UtilsConstraintViolationException e) {dut.fail(e, true);}
			catch (UtilsLockingException e) {dut.fail(e, true);}
			catch (UtilsProcessingException e) {dut.fail(e, true);}
		}
		dbUpdaterTemplate.remove(fReport);
		
		return dut.toDataUpdate();
	}
	
	private TEMPLATE importSystemIoReportTemplate(Template xTemplate) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, UtilsProcessingException
	{
		TEMPLATE eTemplate;
	
		try {eTemplate = fReport.fByCode(cTemplate, xTemplate.getCode());}
		catch (UtilsNotFoundException e)
		{
			eTemplate = efTemplate.build(xTemplate);
			eTemplate = fReport.save(eTemplate);
		}
		eTemplate = efTemplate.update(eTemplate, xTemplate);
		eTemplate = fReport.save(eTemplate);
		eTemplate = efTemplate.updateLD(fReport,eTemplate,xTemplate);
		eTemplate = fReport.load(eTemplate);	
		
		JeeslDbCodeEjbUpdater<CELL> dbuCell = JeeslDbCodeEjbUpdater.createFactory(cCell);
		
		dbuCell.dbEjbs(eTemplate.getCells());
		
		for(Cell xCell : xTemplate.getCell())
		{
			CELL eCell = importCell(eTemplate,xCell);
			dbuCell.handled(eCell);
			
		}
		for(CELL c : dbuCell.getEjbForRemove()){fReport.rmCell(c);}
			
		return eTemplate;
	}
	
	private CELL importCell(TEMPLATE eTemplate, Cell xCell) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, UtilsProcessingException
	{
		CELL eCell;
		try {eCell = fReport.fByCode(cCell, xCell.getCode());}
		catch (UtilsNotFoundException e)
		{
			eCell = efCell.build(eTemplate,xCell);
			eCell = fReport.save(eCell);
		}
		eCell = efCell.update(eCell,xCell);
		eCell = fReport.save(eCell);
		eCell = efCell.updateLD(fReport, eCell, xCell);
		
		return eCell;
	}
	
	@Override public DataUpdate importSystemIoReports(Reports reports)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cReport.getName(),"DB Import"));
		
		JeeslDbCodeEjbUpdater<REPORT> dbUpdaterReport = JeeslDbCodeEjbUpdater.createFactory(cReport);
		dbUpdaterReport.dbEjbs(fReport);
		
		for(Report xReport : reports.getReport())
		{
			try
			{
				REPORT eReport = importSystemIoReport(xReport);
				dbUpdaterReport.handled(eReport);
				dut.success();
			}
			catch (UtilsNotFoundException e) {dut.fail(e, true);}
			catch (UtilsConstraintViolationException e) {dut.fail(e, true);}
			catch (UtilsLockingException e) {dut.fail(e, true);}
			catch (UtilsProcessingException e) {dut.fail(e, true);}
		}
		dbUpdaterReport.remove(fReport);
		
		return dut.toDataUpdate();
	}
	
	private REPORT importSystemIoReport(Report xReport) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, UtilsProcessingException
	{
		REPORT eReport;
		CATEGORY eCategory = fReport.fByCode(cCategory, xReport.getCategory().getCode());
		IMPLEMENTATION eImplementation = fReport.fByCode(cImplementation, xReport.getImplementation().getCode());
		
		try {eReport = fReport.fByCode(cReport, xReport.getCode());}
		catch (UtilsNotFoundException e)
		{
			eReport = efReport.build(eCategory,eImplementation,xReport);
			eReport = fReport.save(eReport);
		}
		eReport = efReport.update(eReport, xReport, eCategory, eImplementation);
		eReport = fReport.save(eReport);
		eReport = efReport.updateLD(fReport,eReport,xReport);
				
		if(xReport.isSetXlsWorkbook())
		{
			importWorkbook(eReport,xReport.getXlsWorkbook());
		}
		
		return eReport;
	}
	
	private REPORT importWorkbook(REPORT eReport, XlsWorkbook xWorkbook) throws UtilsConstraintViolationException, UtilsLockingException, UtilsProcessingException
	{
		WORKBOOK eWorkbook;
		if(eReport.getWorkbook()!=null){eWorkbook = eReport.getWorkbook();}
		else
		{
			eWorkbook = efWorkbook.build(eReport);
			eWorkbook = fReport.save(eWorkbook);
			eReport.setWorkbook(eWorkbook);
		}
		eWorkbook = fReport.load(eWorkbook);
		
		JeeslDbCodeEjbUpdater<SHEET> dbUpdaterSheet = JeeslDbCodeEjbUpdater.createFactory(cSheet);
		dbUpdaterSheet.dbEjbs(eWorkbook.getSheets());
		if(xWorkbook.isSetXlsSheets())
		{
			for(XlsSheet xSheet : xWorkbook.getXlsSheets().getXlsSheet())
			{
				try
				{
					SHEET eSheet = importSheet(eWorkbook,xSheet);
					dbUpdaterSheet.handled(eSheet);
				}
				catch (UtilsNotFoundException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (UtilsConstraintViolationException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (UtilsLockingException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (ExlpXpathNotFoundException e) {throw new UtilsProcessingException(e.getMessage());}
			}
		}
		for(SHEET s : dbUpdaterSheet.getEjbForRemove()){fReport.rmSheet(s);}
		
		return eReport;
	}
	
	private SHEET importSheet(WORKBOOK workbook, XlsSheet xSheet) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, ExlpXpathNotFoundException, UtilsProcessingException
	{
		logger.trace("Importing "+cSheet.getSimpleName()+" "+workbook.getReport().getCategory().getPosition()+"."+workbook.getReport().getPosition()+"."+xSheet.getPosition());
		SHEET eSheet;
		try {eSheet = fReport.fByCode(cSheet, xSheet.getCode());}
		catch (UtilsNotFoundException e)
		{
			eSheet = efSheet.build(workbook,xSheet);
			eSheet = fReport.save(eSheet);
		}
		eSheet = efSheet.update(eSheet,xSheet);
		eSheet = fReport.save(eSheet);
		eSheet = efSheet.updateLD(fReport, eSheet, xSheet);
		eSheet = fReport.load(eSheet,false);
		
		JeeslDbCodeEjbUpdater<GROUP> dbUpdaterGroup = JeeslDbCodeEjbUpdater.createFactory(cGroup);
		JeeslDbCodeEjbUpdater<ROW> dbUpdaterRow = JeeslDbCodeEjbUpdater.createFactory(cRow);
		
		dbUpdaterGroup.dbEjbs(eSheet.getGroups());
		dbUpdaterRow.dbEjbs(eSheet.getRows());
		
		for(Serializable s : xSheet.getContent())
		{
			if(s instanceof ColumnGroup)
			{
				ColumnGroup xGroup = (ColumnGroup)s;
				GROUP eGroup = importGroup(eSheet,xGroup);
				dbUpdaterGroup.handled(eGroup);
			}
			else if(s instanceof Rows)
			{
				Rows xRows = (Rows)s;
				for(Row xRow : xRows.getRow())
				{
					ROW eRow = importRow(eSheet,xRow);
					dbUpdaterRow.handled(eRow);
				}
			}
		}
		for(GROUP g : dbUpdaterGroup.getEjbForRemove()){fReport.rmGroup(g);}
		for(ROW r : dbUpdaterRow.getEjbForRemove()){fReport.rmRow(r);}
		
		return eSheet;
	}
	
	private GROUP importGroup(SHEET eSheet, ColumnGroup xGroup) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, ExlpXpathNotFoundException, UtilsProcessingException
	{
		GROUP eGroup;
		try {eGroup = fReport.fByCode(cGroup, xGroup.getCode());}
		catch (UtilsNotFoundException e)
		{
			eGroup = efGroup.build(eSheet,xGroup);
			eGroup = fReport.save(eGroup);
		}
		eGroup = efGroup.update(eGroup, xGroup);
		eGroup = fReport.save(eGroup);
		eGroup = efGroup.updateLD(fReport,eGroup, xGroup);
		eGroup = fReport.load(eGroup);
		
		JeeslDbCodeEjbUpdater<COLUMN> dbUpdaterColumn = JeeslDbCodeEjbUpdater.createFactory(cColumn);
		dbUpdaterColumn.dbEjbs(eGroup.getColumns());
		for(XlsColumn xColumn : xGroup.getXlsColumn())
		{
			COLUMN eColumn = importColumn(eGroup,xColumn);
			dbUpdaterColumn.handled(eColumn);
		}
		for(COLUMN c : dbUpdaterColumn.getEjbForRemove()){fReport.rmColumn(c);}
		
		return eGroup;
	}
	
	private COLUMN importColumn(GROUP eGroup, XlsColumn xColumn) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, ExlpXpathNotFoundException
	{
		COLUMN eColumn;
		try {eColumn = fReport.fByCode(cColumn, xColumn.getCode());}
		catch (UtilsNotFoundException e)
		{
			eColumn = efColumn.build(fReport,eGroup,xColumn);
			eColumn = fReport.save(eColumn);
		}
		efColumn.update(fReport,eColumn,xColumn);
		eColumn = fReport.save(eColumn);
		eColumn = efColumn.updateLD(fReport,eColumn,xColumn);
		return eColumn;
	}
	
	private ROW importRow(SHEET eSheet, Row xRow) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, ExlpXpathNotFoundException, UtilsProcessingException
	{
		RT eRowType = fReport.fByCode(cRt, xRow.getType().getCode());
		CDT eDataType = null;if(xRow.getDataType()!=null){eDataType = fReport.fByCode(cDataType, xRow.getDataType().getCode());}
		
		ROW eRow;
		try {eRow = fReport.fByCode(cRow, xRow.getCode());}
		catch (UtilsNotFoundException e)
		{
			eRow = efRow.build(eSheet,xRow,eRowType,eDataType);
			eRow = fReport.save(eRow);
		}
		eRow = efRow.update(eRow,xRow,eRowType,eDataType);
		eRow = fReport.save(eRow);
		eRow = efRow.updateLD(fReport,eRow, xRow);

		return eRow;
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Container container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fReport);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }
}