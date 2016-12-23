package org.jeesl.web.rest.system;

import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportsFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestExport;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestImport;
import org.jeesl.model.xml.jeesl.Container;
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
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsWorkbook;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class IoReportRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
									IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
									WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
									SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
									GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
									COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
									CDT extends UtilsStatus<CDT,L,D>,
									ENTITY extends EjbWithId,
									ATTRIBUTE extends EjbWithId,
									FILLING extends UtilsStatus<FILLING,L,D>,
									TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
					implements JeeslIoReportRestExport,JeeslIoReportRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoReportRestService.class);
	
	private JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<REPORT> cReport;
	private final Class<IMPLEMENTATION> cImplementation;
	private final Class<SHEET> cSheet;
	private final Class<FILLING> cFilling;
	private final Class<TRANSFORMATION> cTransformation;

	private XmlContainerFactory xfContainer;
	private XmlReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xfReport;
	
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efReport;
	private EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efWorkbook;
	private EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efSheet;
	
	private JeeslDbLangUpdater<REPORT,L> dbuReportLang;
	private JeeslDbLangUpdater<SHEET,L> dbuSheetLang;
	
	private JeeslDbDescriptionUpdater<REPORT,D> dbuReportDescription;
	private JeeslDbDescriptionUpdater<SHEET,D> dbuSheetDescription;
	
	private IoReportRestService(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<FILLING> cFilling, final Class<TRANSFORMATION> cTransformation,final Class<IMPLEMENTATION> cImplementation)
	{
		this.fReport=fReport;
		this.cL=cL;
		this.cD=cD;
		
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cImplementation=cImplementation;
		this.cSheet=cSheet;
		this.cFilling=cFilling;
		this.cTransformation=cTransformation;

		xfContainer = new XmlContainerFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		xfReport = new XmlReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(ReportQuery.get(ReportQuery.Key.exReport));
		
		ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> ffReport = ReportFactoryFactory.factory(cL, cD, cReport, cWorkbook, cSheet, cGroup, cColumn);
		efReport = ffReport.report();
		efWorkbook = ffReport.workbook();
		efSheet = ffReport.sheet();
		
		dbuReportLang = JeeslDbLangUpdater.factory(cReport, cL);
		dbuSheetLang = JeeslDbLangUpdater.factory(cSheet, cL);
		
		dbuReportDescription = JeeslDbDescriptionUpdater.factory(cReport, cD);
		dbuSheetDescription = JeeslDbDescriptionUpdater.factory(cSheet, cD);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
					IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
					WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
					SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
					GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
					COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
					CDT extends UtilsStatus<CDT,L,D>,
					ENTITY extends EjbWithId,
					ATTRIBUTE extends EjbWithId,
					FILLING extends UtilsStatus<FILLING,L,D>,
					TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
					>
	IoReportRestService<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>
			factory(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<IMPLEMENTATION> cImplementation, final Class<FILLING> cFilling,final Class<TRANSFORMATION> cTransformation)
	{
		return new IoReportRestService<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(fReport,cL,cD,cCategory,cReport,cWorkbook,cSheet,cGroup,cColumn,cFilling,cTransformation,cImplementation);
	}
	
	@Override public Container exportSystemIoReportCategories() {return xfContainer.build(fReport.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoReportSettingFilling() {return xfContainer.build(fReport.allOrderedPosition(cFilling));}
	@Override public Container exportSystemIoReportSettingTransformation() {return xfContainer.build(fReport.allOrderedPosition(cTransformation));}
	@Override public Container exportSystemIoReportSettingImplementation() {return xfContainer.build(fReport.allOrderedPosition(cImplementation));}

	@Override
	public Reports exportSystemIoReports()
	{
		Reports reports = XmlReportsFactory.build();
		for(REPORT report : fReport.all(cReport))
		{
			reports.getReport().add(xfReport.build(report));
		}
		return reports;
	}
	
	@Override public DataUpdate importSystemIoReportCategories(Container categories){return importStatus(cCategory,cL,cD,categories,null);}
	@Override public DataUpdate importSystemIoReportSettingFilling(Container types){return importStatus(cFilling,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportSettingTransformation(Container types){return importStatus(cTransformation,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportSettingImplementation(Container types){return importStatus(cImplementation,cL,cD,types,null);}
	
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
				importSystemIoReport(dbUpdaterReport,xReport);
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
	
	private void importSystemIoReport(JeeslDbCodeEjbUpdater<REPORT> dbUpdaterReport, Report xReport) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, UtilsProcessingException
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
		efReport.update(eReport,xReport,eCategory,eImplementation);
		
		eReport=dbuReportLang.handle(fReport, eReport, xReport.getLangs());eReport = fReport.save(eReport);
		eReport=dbuReportDescription.handle(fReport, eReport, xReport.getDescriptions());eReport = fReport.save(eReport);
		
		if(xReport.isSetXlsWorkbook())
		{
			eReport = importWorkbook(eReport,xReport.getXlsWorkbook());
			eReport = fReport.save(eReport);
		}
		
		dbUpdaterReport.handled(eReport);
		eReport = fReport.save(eReport);
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
		
		JeeslDbCodeEjbUpdater<SHEET> dbUpdaterSheet = JeeslDbCodeEjbUpdater.createFactory(cSheet);
		dbUpdaterSheet.dbEjbs(fReport);
		if(xWorkbook.isSetXlsSheets())
		{
			for(XlsSheet xSheet : xWorkbook.getXlsSheets().getXlsSheet())
			{
				try
				{
					importSystemIoSheet(dbUpdaterSheet,eWorkbook,xSheet);
				}
				catch (UtilsNotFoundException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (UtilsConstraintViolationException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (UtilsLockingException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (ExlpXpathNotFoundException e) {throw new UtilsProcessingException(e.getMessage());}
			}
		}
		
		dbUpdaterSheet.remove(fReport);
		
		return eReport;
	}
	
	private void importSystemIoSheet(JeeslDbCodeEjbUpdater<SHEET> dbUpdaterSheet, WORKBOOK workbook, XlsSheet xSheet) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException, ExlpXpathNotFoundException
	{
		SHEET eSheet;
		
		try {eSheet = fReport.fByCode(cSheet, xSheet.getCode());}
		catch (UtilsNotFoundException e)
		{
			eSheet = efSheet.build(workbook,xSheet);
			eSheet = fReport.save(eSheet);
		}
		efSheet.update(eSheet, xSheet);
		
		eSheet=dbuSheetLang.handle(fReport, eSheet, ReportXpath.getLangs(xSheet));eSheet = fReport.save(eSheet);
		eSheet=dbuSheetDescription.handle(fReport, eSheet, ReportXpath.getDescriptions(xSheet));eSheet = fReport.save(eSheet);	
		
		dbUpdaterSheet.handled(eSheet);
		eSheet = fReport.save(eSheet);
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