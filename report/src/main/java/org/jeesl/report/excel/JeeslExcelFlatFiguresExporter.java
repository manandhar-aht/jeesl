package org.jeesl.report.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.model.json.JsonFlatFigure;

import org.jeesl.model.json.JsonFlatFigures;
import org.slf4j.LoggerFactory;

public class JeeslExcelFlatFiguresExporter<L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
											IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
											WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
											SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
											GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
											COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
											ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
											CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
											RT extends UtilsStatus<RT,L,D>,
											ENTITY extends EjbWithId,
											ATTRIBUTE extends EjbWithId,
											FILLING extends UtilsStatus<FILLING,L,D>,
											TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(JeeslExcelFlatFiguresExporter.class);
	
    // Excel related objects
    public Workbook         wb;
    public Font             headerFont;
    public CellStyle        dateHeaderStyle;
    public CellStyle		titleStyle;
    public CellStyle        numberStyle; 
    public CreationHelper   createHelper;
    public JXPathContext	context;
	
    // The sheet in the workbook
    private Sheet sheet;

    // The current row and column number
    private short rowNr          = 0;
    private short column         = 0;

    public JeeslExcelFlatFiguresExporter()
    {
        
	}
	
	public void init(Workbook wb) {
		// Create the Excel Workbook and select the created sheet
        createHelper = wb.getCreationHelper();

        // Create fonts and alter it.
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
        Font fontTitle = wb.createFont();
        fontTitle.setItalic(true);

        // Create standard styles
        dateHeaderStyle = wb.createCellStyle();
        dateHeaderStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
        dateHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateHeaderStyle.setFont(font);
        dateHeaderStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        dateHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        dateHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
        numberStyle = wb.createCellStyle();
        numberStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		
        // Create header style
        titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(fontTitle);
	}
	
	public byte[] export(List<String> headers, JsonFlatFigures figures)
	{
		// Reset row and sheet
		rowNr	= 0;
		wb		= new XSSFWorkbook();
		
		// Call Init
		init(wb);
		
		sheet	= wb.createSheet("Aggregations");
		Row row = sheet.createRow(rowNr);

		for (String header : headers)
		{
				Cell cell   = row.createCell(column);

				cell.setCellStyle(titleStyle);
				cell.setCellValue(header);
				column++;
		}

		// Reset column number and go to next row
		column = 0;
		rowNr++;

		// Fill in data rows
		for (JsonFlatFigure content : figures.getFigures())
		{
			row     = sheet.createRow(rowNr);
			createNextCell(row, content.getG1());
			createNextCell(row, content.getG2());
			createNextCell(row, content.getG3());
			createNextCell(row, content.getG4());
			createNextCell(row, content.getG5());
			createNextCell(row, content.getG6());
			createNextCell(row, content.getG7());
			createNextCell(row, content.getG8());
			createNextCell(row, content.getG9());
			createNextCell(row, content.getG10());
			createNextCell(row, content.getG11());
			column = 0;
			rowNr++;
		}
		for (int i = 1; i < 12; i++)
		{
			column++;
			sheet.autoSizeColumn(i);
		}
		return getSheet();
	}
	
	public byte[] exportByColumns(List<COLUMN> columns, JsonFlatFigures figures)
	{
		// Reset row and sheet
		rowNr	= 0;
		wb		= new XSSFWorkbook();
		
		// Call Init
		init(wb);
		
		sheet	= wb.createSheet("Data");
		
		// Fill in data rows
		for (JsonFlatFigure content : figures.getFigures())
		{
			Row row = sheet.createRow(rowNr);
		
			// Add data for all defined columns
			for (COLUMN columnDefinition : columns)
			{
				Cell cell   = row.createCell(column);
				Object value = getRequestedValue(columnDefinition.getQueryCell(), content);
				if (value != null)
				{
					if (columnDefinition.getQueryCell().startsWith("g")) {cell.setCellValue(value.toString());}
					if (columnDefinition.getQueryCell().startsWith("d")) {
						double d = (Double) value; cell.setCellValue(d);
						cell.setCellStyle(numberStyle);}
					logger.trace("Adding " +value.toString() +" to cell " +row.getRowNum() +"," +column);
				}
				column++;
			}
			column = 0;
			rowNr++;
		}
		for (int i = 1; i < 12; i++)
		{
			column++;
			sheet.autoSizeColumn(i);
		}
		return getSheet();
	}
	
	public byte[] getSheet()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
				wb.write(baos);
		} catch (IOException ex) {
				logger.error(ex.getMessage());
		}
		return baos.toByteArray();
	}
	
	public void createNextCell(Row row, String content)
	{
		if (content != null)
		{
				Cell cell   = row.createCell(column);
				cell.setCellValue(content);
		}
		column++;
	}
	
	public Object getRequestedValue(String query, JsonFlatFigure content)
	{
		if (query!= null)
		{
			if (query.equals("g1")) {if (content.getG1()!=null) {return content.getG1();}}
			if (query.equals("g2")) {if (content.getG2()!=null) {return content.getG2();}}
			if (query.equals("g3")) {if (content.getG3()!=null) {return content.getG3();}}
			if (query.equals("g4")) {if (content.getG4()!=null) {return content.getG4();}}
			if (query.equals("g5")) {if (content.getG5()!=null) {return content.getG5();}}
			if (query.equals("g6")) {if (content.getG6()!=null) {return content.getG6();}}
			if (query.equals("g7")) {if (content.getG7()!=null) {return content.getG7();}}
			if (query.equals("g8")) {if (content.getG8()!=null) {return content.getG8();}}
			if (query.equals("g9")) {if (content.getG9()!=null) {return content.getG9();}}
			if (query.equals("g10")) {if (content.getG10()!=null) {return content.getG10();}}
			
			if (query.equals("d1")) {if (content.getD1()!=null) {return content.getD1();}}
			if (query.equals("d2")) {if (content.getD2()!=null) {return content.getD2();}}
			if (query.equals("d3")) {if (content.getD3()!=null) {return content.getD3();}}
			if (query.equals("d4")) {if (content.getD4()!=null) {return content.getD4();}}
			if (query.equals("d5")) {if (content.getD5()!=null) {return content.getD5();}}
			if (query.equals("d6")) {if (content.getD6()!=null) {return content.getD6();}}
			if (query.equals("d7")) {if (content.getD7()!=null) {return content.getD7();}}
			if (query.equals("d8")) {if (content.getD8()!=null) {return content.getD8();}}
			if (query.equals("d9")) {if (content.getD9()!=null) {return content.getD9();}}
			if (query.equals("d10")) {if (content.getD10()!=null) {return content.getD10();}}
		}
		return null;
	}
}