package org.jeesl.factory.ejb.system.io.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoReportFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportFactoryFactory.class);
	
	final Class<L> cL;
	final Class<D> cD;
	final Class<REPORT> cReport;
	final Class<WORKBOOK> cWorkbook;
	final Class<SHEET> cSheet;
	final Class<GROUP> cGroup;
	final Class<COLUMN> cColumn;
    
	private EjbIoReportFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn)
	{       
		this.cL = cL;
        this.cD = cD;
        this.cReport = cReport;
        this.cWorkbook = cWorkbook;
        this.cSheet = cSheet;
        this.cGroup = cGroup;
        this.cColumn = cColumn;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
					FILLING extends UtilsStatus<FILLING,L,D>,
					TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
	EjbIoReportFactoryFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> factory(final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn)
	{
		return new EjbIoReportFactoryFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>(cL,cD,cReport,cWorkbook,cSheet,cGroup,cColumn);
	}
	
	public EjbIoReportFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> report()
	{
		return new EjbIoReportFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>(cL,cD,cReport);
	}
	
	public EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> workbook()
	{
		return new EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>(cL,cD,cWorkbook);
	}
	
	public EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> sheet()
	{
		return new EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>(cL,cD,cSheet);
	}
	
	public EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> group()
	{
		return new EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>(cL,cD,cGroup);
	}
	
	public EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> column()
	{
		return new EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>(cL,cD,cColumn);
	}
}