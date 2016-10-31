package org.jeesl.report.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
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
import org.jeesl.model.json.JsonFlatFigure;

import org.jeesl.model.json.JsonFlatFigures;
import org.slf4j.LoggerFactory;

public class JeeslExcelFlatFiguresExporter
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
	private final Sheet sheet;
    
	// The current row number
	private short rowNr          = 0;
	
	public JeeslExcelFlatFiguresExporter()
	{
		// Create the Excel Workbook and select the created sheet
		wb = new XSSFWorkbook();
		wb.createSheet("Aggregations");
		sheet = wb.getSheet("Aggregations");
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
		// Create the column headers
		short column        = 0;
		Row row     = sheet.createRow(rowNr);
		
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
			for (int i = 1; i < 10; i++)
			{
				Object o = null;
				try {
						o = BeanUtils.getProperty(content, "g" +i);
					} catch (IllegalAccessException ex) {
						logger.error(ex.getMessage());
					} catch (InvocationTargetException ex) {
						logger.error(ex.getMessage());
					} catch (NoSuchMethodException ex) {
						logger.error(ex.getMessage());
				}
				if (o != null)
				{
					Cell cell   = row.createCell(column);
					cell.setCellValue(o.toString());
				}
				column++;
				sheet.autoSizeColumn(i);
			}
			column = 0;
			rowNr++;
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
}
