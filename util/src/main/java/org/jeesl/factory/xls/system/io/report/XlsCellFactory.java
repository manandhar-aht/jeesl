package org.jeesl.factory.xls.system.io.report;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
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

public class XlsCellFactory <L extends UtilsLang,D extends UtilsDescription,
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
							ATTRIBUTE extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(XlsCellFactory.class);
		
	private String localeCode;
	
	private XlsCellStyleProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> cellStyleProvider;
	
	public XlsCellFactory(String localeCode, XlsCellStyleProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> cellStyleProvider)
	{
		this.localeCode = localeCode;
		this.cellStyleProvider=cellStyleProvider;
	}
	
	public void build(COLUMN ioColumn, Row xlsRow, MutableInt columnNr, JXPathContext relativeContext)
	{
		Object value = relativeContext.getValue(ioColumn.getQueryCell());
		if(value!=null)
		{
			XlsCellFactory.build(xlsRow,columnNr,cellStyleProvider.get(ioColumn),value.toString());
		}
		else
		{
			columnNr.add(1);
		}
	}
	
	public void label(Row xlsRow, MutableInt columnNr, ROW ioRow)
	{
		XlsCellFactory.build(xlsRow,columnNr,cellStyleProvider.getStyleLabelLeft(),ioRow.getName().get(localeCode).getLang());
	}
	
	public void value(Row xlsRow, MutableInt columnNr, ROW ioRow, JXPathContext context)
	{
		Object value = context.getValue(ioRow.getQueryCell());
		if(value==null){columnNr.add(1);}
		else{XlsCellFactory.build(xlsRow,columnNr,cellStyleProvider.get(ioRow),value.toString());}
	}
	
	public static void build(Row xlsRow, MutableInt columnNr, CellStyle style, String value)
	{
		Cell cell = xlsRow.createCell(columnNr.intValue());
        cell.setCellStyle(style);
        cell.setCellValue(value);
        columnNr.add(1);
	}
}