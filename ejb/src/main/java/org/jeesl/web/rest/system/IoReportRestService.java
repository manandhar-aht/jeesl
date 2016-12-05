package org.jeesl.web.rest.system;

import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportFactory;
import org.jeesl.factory.xml.system.io.report.XmlReportsFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestExport;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestImport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class IoReportRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									FILLING extends UtilsStatus<FILLING,L,D>,
									TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
									IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>>
					implements JeeslIoReportRestExport,JeeslIoReportRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoReportRestService.class);
	
	private JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<FILLING> cFilling;
	private final Class<TRANSFORMATION> cTransformation;
	private final Class<IMPLEMENTATION> cImplementation;

	private XmlStatusFactory xfStatus;
	private XmlReportFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION,IMPLEMENTATION> xfReport;
	
//	private EjbLangFactory<L> efLang;
//	private EjbDescriptionFactory<D> efDescription;
	
	private IoReportRestService(JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<FILLING> cFilling, final Class<TRANSFORMATION> cTransformation,final Class<IMPLEMENTATION> cImplementation)
	{
		this.fReport=fReport;
		this.cL=cL;
		this.cD=cD;
		
		this.cCategory=cCategory;
		this.cFilling=cFilling;
		this.cTransformation=cTransformation;
		this.cImplementation=cImplementation;
	
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					FILLING extends UtilsStatus<FILLING,L,D>,
					TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
					IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>>
	IoReportRestService<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION,IMPLEMENTATION>
			factory(JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<FILLING> cFilling,final Class<TRANSFORMATION> cTransformation,final Class<IMPLEMENTATION> cImplementation)
	{
		return new IoReportRestService<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION,IMPLEMENTATION>(fReport,cL,cD,cCategory,cFilling,cTransformation,cImplementation);
	}
	
	@Override public Container exportSystemIoReportCategories() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cCategory)));}
	@Override public Container exportSystemIoReportSettingFilling() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cFilling)));}
	@Override public Container exportSystemIoReportSettingTransformation() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cTransformation)));}
	@Override public Container exportSystemIoReportSettingImplementation() {return XmlContainerFactory.buildStatusList(xfStatus.build(fReport.allOrderedPosition(cImplementation)));}

	@Override public DataUpdate importSystemIoReportCategories(Container categories){return importStatus(cCategory,cL,cD,categories,null);}
	@Override public DataUpdate importSystemIoReportSettingFilling(Container types){return importStatus(cFilling,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportSettingTransformation(Container types){return importStatus(cTransformation,cL,cD,types,null);}
	@Override public DataUpdate importSystemIoReportSettingImplementation(Container types){return importStatus(cImplementation,cL,cD,types,null);}
	
	@Override
	public Reports exportSystemIoReports()
	{
		Reports reports = XmlReportsFactory.build();
		
		return reports;
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