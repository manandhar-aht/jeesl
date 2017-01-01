package org.jeesl.factory.xls.system.io.report;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class XlsCellFactory <L extends UtilsLang,D extends UtilsDescription,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
							WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							STYLE extends JeeslReportStyle<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
							RT extends UtilsStatus<RT,L,D>,
							ENTITY extends EjbWithId,
							ATTRIBUTE extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(XlsCellFactory.class);
		
	private String localeCode;
	
	private XlsCellStyleFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE> xfStyle;
	
	public XlsCellFactory(String localeCode, XlsCellStyleFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE> xfStyle)
	{
		this.localeCode = localeCode;
		this.xfStyle=xfStyle;
	}
	
	public void header(GROUP ioGroup, Row xlsRow, MutableInt columnNr) {XlsCellFactory.build(xlsRow,columnNr,xfStyle.get(JeeslReportLayout.Style.header,ioGroup),ioGroup.getName().get(localeCode).getLang());}
	public void header(COLUMN ioColumn, Row xlsRow, MutableInt columnNr) {XlsCellFactory.build(xlsRow,columnNr,xfStyle.get(JeeslReportLayout.Style.header,ioColumn),ioColumn.getName().get(localeCode).getLang());}
	
	public void cell(COLUMN ioColumn, Row xlsRow, MutableInt columnNr, JXPathContext context)
	{
		try
		{
			Object value = context.getValue(ioColumn.getQueryCell());
			if(value!=null)
			{
				XlsCellFactory.build(xlsRow,columnNr,xfStyle.get(JeeslReportLayout.Style.cell,ioColumn),value.toString());
			}
			else {columnNr.add(1);}
		}
		catch(JXPathNotFoundException e){columnNr.add(1);}
	}
	
	public void label(Row xlsRow, MutableInt columnNr, ROW ioRow)
	{
		XlsCellFactory.build(xlsRow,columnNr,xfStyle.getStyleLabelLeft(),ioRow.getName().get(localeCode).getLang());
	}
	
	public void value(Row xlsRow, MutableInt columnNr, ROW ioRow, JXPathContext context)
	{
		try
		{
			Object value = context.getValue(ioRow.getQueryCell());
			if(value==null){columnNr.add(1);}
			else{XlsCellFactory.build(xlsRow,columnNr,xfStyle.get(ioRow),value.toString());}
		}
		catch (JXPathNotFoundException e){columnNr.add(1);}
	}
	
	public void build(Sheet xslSheet, MutableInt rowNr, ROW ioRow)
	{
		int rootRow = rowNr.intValue();
		int rootColumn = ioRow.getOffsetColumns();
		int maxRow = rootRow;
		
		for(CELL ioCell : ioRow.getTemplate().getCells())
		{
			int cellRow = rootRow+ioCell.getRowNr()-1;
			int cellCol = rootColumn+ioCell.getColNr()-1;
			if(cellRow>maxRow){maxRow=cellRow;}
			
			logger.info("Creating Row "+cellRow+"."+cellCol);
			Row xlsRow = xslSheet.getRow(cellRow); if(xlsRow==null){xlsRow = xslSheet.createRow(cellRow);}
			
			Cell xlsCell = xlsRow.createCell(cellCol);
			xlsCell.setCellStyle(xfStyle.getStyleFallback());
			xlsCell.setCellValue(ioCell.getName().get(localeCode).getLang());
		}
		rowNr.add(maxRow-rootRow+1);
	}
	
	public static void build(Row xlsRow, MutableInt columnNr, CellStyle style, String value)
	{
		Cell cell = xlsRow.createCell(columnNr.intValue());
        cell.setCellStyle(style);
        cell.setCellValue(value);
        columnNr.add(1);
	}
}