package org.jeesl.report.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
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

    // The current row and column number
    private short rowNr          = 0;
    private short column         = 0;

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
}