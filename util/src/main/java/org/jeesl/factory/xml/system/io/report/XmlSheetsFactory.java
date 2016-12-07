package org.jeesl.factory.xml.system.io.report;

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
import net.sf.ahtutils.xml.report.XlsSheets;

public class XmlSheetsFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								CDT extends UtilsStatus<CDT,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSheetsFactory.class);
		
	private XlsSheets q;
	
	private XmlSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,FILLING,TRANSFORMATION> xfSheet;

	public XmlSheetsFactory(String localeCode, XlsSheets q)
	{
		this.q=q;
		if(q.isSetXlsSheet()){xfSheet = new XmlSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,FILLING,TRANSFORMATION>(localeCode,q.getXlsSheet().get(0));}
	}
	
	public XlsSheets build(WORKBOOK workbook)
	{
		XlsSheets xml = build();
		
		if(q.isSetXlsSheet())
		{
			for(SHEET sheet : workbook.getSheets())
			{
				xml.getXlsSheet().add(xfSheet.build(sheet));
			}
		}
						
		return xml;
	}
	
	public static XlsSheets build()
	{
		XlsSheets xml = new XlsSheets();						
		return xml;
	}
}