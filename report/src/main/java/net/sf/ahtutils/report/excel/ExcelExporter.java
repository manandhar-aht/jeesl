package net.sf.ahtutils.report.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;
import net.sf.ahtutils.interfaces.controller.report.UtilsXlsDefinitionResolver;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsWorkbook;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.xpath.StatusXpath;

public class ExcelExporter
{
	
    // Excel related objects
    public Workbook         wb;
    public Font             headerFont;
    public CellStyle        dateHeaderStyle;
    public CellStyle        numberStyle; 
    public CreationHelper   createHelper;
    
    
    // The data
    public Object    report;
    
    // The query which entities are the subject of this report
    public String    query;
    
    // How many results are there for the given query
    public Integer   counter;
        
    public Hashtable<String, CellStyle> cellStyles = new Hashtable<String, CellStyle>();
    public Hashtable<String, Integer> errors = new Hashtable<String, Integer>();
	
    final static Logger logger = LoggerFactory.getLogger(ExcelExporter.class);
	
    public ExcelExporter(UtilsXlsDefinitionResolver resolver, String id, Object report, String listDefinition, String languageKey) throws Exception
    {
		// Get all info
        this.query      = listDefinition;
        this.report     = report;
        
		
        // Create a new Excel Workbook and a POI Helper Object
        wb = new XSSFWorkbook();
        createHelper = wb.getCreationHelper();

        // Create a new font and alter it.
        Font font = wb.createFont();
        font.setItalic(true);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Create styles
        dateHeaderStyle = wb.createCellStyle();
        dateHeaderStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
        dateHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        dateHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateHeaderStyle.setFont(font);

        numberStyle = wb.createCellStyle();
        numberStyle.setDataFormat(createHelper.createDataFormat().getFormat("#.00\\ RWF"));

        // Search for the right Workbook definition in the XlsDefinition
        XlsWorkbook workbook = resolver.definition(id);
        
        if (workbook == null)
        {
                logger.error("Xml Workbook not found in Definition.");
                workbook = new XlsWorkbook();
        }

        // Now lets create the sheets and export the data
		int i = 1;
        for (XlsSheet sheet : workbook.getXlsSheet())
        {
			// First check for an existing name given in the definition
			String sheetName = "sheet" +i;
			
			if (sheet.isSetLangs())
			{
				if (sheet.getLangs().isSetLang())
				{
					Langs langs = sheet.getLangs();
					Lang  lang  = StatusXpath.getLang(langs, languageKey);
					if (lang != null)
					{
						sheetName = lang.getTranslation();
					}
				}
			}
            exportSheet(sheet, sheetName);
			i++;
        }
    }

    public void exportSheet(XlsSheet sheetDefinition, String id) throws Exception
    {
		logger.debug("Creating Sheet " +id);
        // Create JXPath context for working with the report data
        JXPathContext context = JXPathContext.newContext(report);
        
        // Create Excel Sheet named as given in constructor
        Sheet sheet = wb.createSheet(id);
        
		
        // PreProcess columns to create Styles and count the number of results for the given report query
        ArrayList<XlsColumn> sortedColumns = preProcessColumns(sheetDefinition);
		logger.debug("PreProcess complete. Got " +sortedColumns.size() +" columns.");
        // Create Headers
        ArrayList<String> headers = new ArrayList<String>();
        for (XlsColumn column : sortedColumns)
        {
            headers.add(column.getLangs().getLang().get(0).getTranslation());
                            errors.put(column.getLangs().getLang().get(0).getTranslation(), 0);
        }
        String[] headerArray = new String[headers.size()];
        createHeader(sheet, headers.toArray(headerArray));

        // Create Content Rows
		String queryExpression = sheetDefinition.getQuery();
		logger.info("Iterating to find " +queryExpression);
		Iterator iterator     = context.iteratePointers(queryExpression);
        int rowNr = 1;
		logger.debug("Beginning iteration");
        while (iterator.hasNext())
        {
            Pointer pointerToItem = (Pointer)iterator.next();
			logger.trace("Got pointer: " +pointerToItem.getValue().getClass());
			JXPathContext relativeContext = context.getRelativeContext(pointerToItem);
			
			for (int i = 0; i<sortedColumns.size(); i++)
            {
                XlsColumn columnDefinition = sortedColumns.get(i);
                CellStyle   style = cellStyles.get(columnDefinition.getColumn());
                String      type  = columnDefinition.getXlsTransformation().getDataClass();
                String expression = columnDefinition.getXlsTransformation().getBeanProperty();
                String columnId   = columnDefinition.getLangs().getLang().get(0).getTranslation();
                try {
                    Object  value = null;
                    //String xpath  = query +"[" +row +"]/" + expression;
                    logger.trace("Using XPath expression: " +expression);
                    value = relativeContext.getValue(expression);
                    logger.trace("Got Value " +value.toString());
                    createCell(sheet, rowNr, i, value, type, style);

                } catch (Exception e)
                {
                    Integer counter = errors.get(columnId);
                    counter++;
                    errors.put(columnId, counter);
                }
            }
			
			rowNr++;
        }
        logger.info("Processed " +rowNr +" entries.");
        
        fixWidth(sheet);
        for (String key : errors.keySet())
        {
            if (errors.get(key)>0)
            {
                logger.error(errors.get(key) +" cell creation errors in " +key);
            }
        }

    }

    public CellStyle getCellStyle(XlsColumn columnDefinition)
    {
		// Refer to POI standard styles at https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/BuiltinFormats.html
        CellStyle style = wb.createCellStyle();
		String dataClass = columnDefinition.getXlsTransformation().getDataClass();
		if (dataClass.equalsIgnoreCase("String"))
		{
			// BuiltIn Style:  0x31 "text" - Alias for "@"
			style.setDataFormat(createHelper.createDataFormat().getFormat("text"));
		} 
		else if (dataClass.equalsIgnoreCase("Integer"))
		{
			// BuiltIn Style: 1, "0"
			style.setDataFormat((short) 1);
		} else if (columnDefinition.getXlsTransformation().isSetFormatPattern())
		{
			// Use custom style
			style.setDataFormat(createHelper.createDataFormat().getFormat(columnDefinition.getXlsTransformation().getFormatPattern()));
		}
        return style;
    }

    public Sheet createHeader(Sheet sheet, String[] headers)
    {
        Row     headerRow = sheet.createRow(0);
        Integer cellNr = 0;
        for (String header : headers)
        {
            Cell cell = headerRow.createCell(cellNr);
                            cell.setCellStyle(dateHeaderStyle);
                            cell.setCellValue(header);
            cellNr++;
        }
        return sheet;
    }

    public Sheet createCell(Sheet sheet, Integer rowNr, Integer cellNr, Object value, String type, CellStyle style)
    {
        Row     row = sheet.getRow(rowNr) != null ? sheet.getRow(rowNr) : sheet.createRow(rowNr);
        Cell   cell = row.createCell(cellNr);
        if      (type.equals("String"))                 {cell.setCellValue(value.toString());}
        else if (type.equals("Date"))   {
                                                    XMLGregorianCalendar calendar = (XMLGregorianCalendar) value;
                                                    cell.setCellValue(new Date(calendar.toGregorianCalendar().getTimeInMillis()));
                                                    cell.setCellStyle(style);
                                                }
        else if (type.equals("Number"))   {
                                                    Double number = (Double) value;
                                                    cell.setCellValue(number);
                                                    cell.setCellStyle(style);
                                                }
        else if (type.equals("Integer"))   {
                                                    cell.setCellValue(Integer.parseInt("" +value));
                                                }
        else if (type.equals("Boolean"))   {
                                                    Boolean bool = (Boolean) value;
                                                    cell.setCellValue(bool);
                                                }
        return sheet;
    }

    public Sheet fixWidth(Sheet sheet)
    {
        for (int i = 0; i<8; i++)
            {
                    sheet.autoSizeColumn(i);
            }
        return sheet;
    }

    public ArrayList<XlsColumn> preProcessColumns(XlsSheet sheet)
    {
        ArrayList<XlsColumn> columns = new ArrayList<XlsColumn>();
        TreeMap<String, XlsColumn> tempListForSorting = new TreeMap<String, XlsColumn>();
        for (XlsColumn column : sheet.getXlsColumn())
        {
            // TODO Decide if this one is exported by reading isExported later.
            tempListForSorting.put(column.getColumn(), column);
        }
        for (String key : tempListForSorting.keySet())
        {
            columns.add(tempListForSorting.get(key));
            cellStyles.put(key, getCellStyle(tempListForSorting.get(key)));
        }
        return columns;
    }

    public Workbook getWb() {return wb;}
    public void setWb(Workbook wb) {this.wb = wb;}
}