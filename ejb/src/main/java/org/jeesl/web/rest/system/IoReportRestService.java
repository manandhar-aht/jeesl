package org.jeesl.web.rest.system;

import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportsFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
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
import org.jeesl.util.ejb.JeeslDbCodeEjbUpdater;
import org.jeesl.util.ejb.JeeslDbDescriptionUpdater;
import org.jeesl.util.ejb.JeeslDbLangUpdater;
import org.jeesl.util.query.xml.ReportQuery;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

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
	private final Class<FILLING> cFilling;
	private final Class<TRANSFORMATION> cTransformation;

	@SuppressWarnings("rawtypes")
	private XmlStatusFactory xfStatus;
	private XmlReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xfReport;
	
//	private EjbLangFactory<L> efLang;
//	private EjbDescriptionFactory<D> efDescription;
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efReport;
	
	private final JeeslDbLangUpdater<REPORT,L> dbUpdaterLang;;
	private final JeeslDbDescriptionUpdater<REPORT,D> dbUpdaterDescription;
	
	private IoReportRestService(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<FILLING> cFilling, final Class<TRANSFORMATION> cTransformation,final Class<IMPLEMENTATION> cImplementation)
	{
		this.fReport=fReport;
		this.cL=cL;
		this.cD=cD;
		
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cFilling=cFilling;
		this.cTransformation=cTransformation;
		this.cImplementation=cImplementation;
	
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		xfReport = new XmlReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(ReportQuery.get(ReportQuery.Key.exReport));
		
		ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> ffReport = ReportFactoryFactory.factory(cL, cD, cReport, cWorkbook, cSheet, cGroup, cColumn);
		efReport = ffReport.report();
		
		dbUpdaterLang = JeeslDbLangUpdater.factory(cReport, cL);
		dbUpdaterDescription = JeeslDbDescriptionUpdater.factory(cReport, cD);
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
	
	@Override public Container exportSystemIoReportCategories() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cCategory)));}
	@Override public Container exportSystemIoReportSettingFilling() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cFilling)));}
	@Override public Container exportSystemIoReportSettingTransformation() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cTransformation)));}
	@Override public Container exportSystemIoReportSettingImplementation() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cImplementation)));}

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
		}
		
		dbUpdaterReport.remove(fReport);
		
		return dut.toDataUpdate();
	}
	
	private void importSystemIoReport(JeeslDbCodeEjbUpdater<REPORT> dbUpdaterReport, Report xReport) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
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
		
		eReport.setCategory(eCategory);
		eReport.setImplementation(eImplementation);
		
		dbUpdaterLang.handle(fReport, eReport, xReport.getLangs());eReport = fReport.save(eReport);
		dbUpdaterDescription.handle(fReport, eReport, xReport.getDescriptions());eReport = fReport.save(eReport);
		
		dbUpdaterReport.handled(eReport);
		eReport = fReport.save(eReport);
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