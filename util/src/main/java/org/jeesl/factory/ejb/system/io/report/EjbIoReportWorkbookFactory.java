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

public class EjbIoReportWorkbookFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								CDT extends UtilsStatus<CDT,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportWorkbookFactory.class);
	
	final Class<WORKBOOK> cWorkbook;
    
	protected EjbIoReportWorkbookFactory(final Class<L> cL,final Class<D> cD,final Class<WORKBOOK> cWorkbook)
	{       
        this.cWorkbook = cWorkbook;
	}
	    
	public WORKBOOK build(REPORT report)
	{
		WORKBOOK ejb = null;
		try
		{
			ejb = cWorkbook.newInstance();
			ejb.setReport(report);
			ejb.getReport().setWorkbook(ejb);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}