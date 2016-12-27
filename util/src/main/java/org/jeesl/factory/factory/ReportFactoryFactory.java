package org.jeesl.factory.factory;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellStyleProvider;
import org.jeesl.factory.xls.system.io.report.XlsRowFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class ReportFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
										IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
										WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
										SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
										GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
										COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
										ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
										CDT extends UtilsStatus<CDT,L,D>,
										RT extends UtilsStatus<RT,L,D>,
										ENTITY extends EjbWithId,
										ATTRIBUTE extends EjbWithId,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(ReportFactoryFactory.class);
	
	final Class<L> cL;
	final Class<D> cD;
	final Class<REPORT> cReport;
	final Class<WORKBOOK> cWorkbook;
	final Class<SHEET> cSheet;
	final Class<GROUP> cGroup;
	final Class<COLUMN> cColumn;
	final Class<ROW> cRow;
    
	private ReportFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow)
	{       
		this.cL = cL;
        this.cD = cD;
        this.cReport = cReport;
        this.cWorkbook = cWorkbook;
        this.cSheet = cSheet;
        this.cGroup = cGroup;
        this.cColumn = cColumn;
        this.cRow = cRow;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
					IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
					WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
					SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
					GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
					COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
					ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
					CDT extends UtilsStatus<CDT,L,D>,
					RT extends UtilsStatus<RT,L,D>,
					ENTITY extends EjbWithId,
					ATTRIBUTE extends EjbWithId,
					FILLING extends UtilsStatus<FILLING,L,D>,
					TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
	ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> factory(final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow)
	{
		return new ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cReport,cWorkbook,cSheet,cGroup,cColumn,cRow);
	}
	
	public EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> report()
	{
		return new EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cReport);
	}
	
	public EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> workbook()
	{
		return new EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cWorkbook);
	}
	
	public EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> sheet()
	{
		return new EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cSheet);
	}
	
	public EjbIoReportRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> row()
	{
		return new EjbIoReportRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cRow);
	}
	
	public EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> group()
	{
		return new EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cGroup);
	}
	
	public EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> column()
	{
		return new EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(cL,cD,cColumn);
	}
	
	public XlsRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xlsRow(String localeCode)
	{
		return new XlsRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>(localeCode);
	}
	
	public XlsCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xlsCell(XlsCellStyleProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> styleProvider)
	{
		return new XlsCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>(styleProvider);
	}
	
	public XlsCellStyleProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xlsCellStyleProvider(Workbook xlsWorkbook, List<COLUMN> ioColumns)
	{
		return new XlsCellStyleProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>(xlsWorkbook,ioColumns);
	}
}