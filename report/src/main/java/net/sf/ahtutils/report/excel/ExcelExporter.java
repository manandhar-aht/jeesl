package net.sf.ahtutils.report.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
import net.sf.ahtutils.xml.report.XlsMultiColumn;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsTransformation;
import net.sf.ahtutils.xml.report.XlsWorkbook;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.ahtutils.xml.xpath.StatusXpath;

public class ExcelExporter
{
	
    // Excel related objects
    public Workbook         wb;
    public Font             headerFont;
    public CellStyle        dateHeaderStyle;
    public CellStyle        numberStyle; 
    public CreationHelper   createHelper;
    public JXPathContext	context;
    
    // The data
    public Object    report;
    
    // The query which entities are the subject of this report
    public String    query;
    
    // How many results are there for the given query
    public Integer   counter;
	
	// Current line while exporting
	private int      rowNr = 1;
	
	// Languge
	private String   languageKey;
        
    public Hashtable<String, CellStyle> cellStyles = new Hashtable<String, CellStyle>();
    public Hashtable<String, Integer> errors = new Hashtable<String, Integer>();
	
    final static Logger logger = LoggerFactory.getLogger(ExcelExporter.class);
	private int MIN_WIDTH = 5000;
	
    public ExcelExporter(UtilsXlsDefinitionResolver resolver, String id, Object report, String listDefinition, String languageKey) throws Exception
    {
		// Get all info
        this.query      = listDefinition;
        this.report     = report;
        this.languageKey= languageKey;
		
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
			// Reset the rowCounter
			rowNr = 1;
			
			// First check for an existing name given in the definition
			String sheetName = "sheet" +i;
			
			try {
			Langs langs = ReportXpath.getLangs(sheet.getContent());
			Lang  lang  = StatusXpath.getLang(langs, languageKey);
			if (lang != null)
			{
				logger.info("Setting Sheetname to " +lang.getTranslation());
				
				sheetName = lang.getTranslation();
			}}
			catch (Exception e)
			{
				logger.warn(e.getMessage());
				logger.warn("Could not retrieve sheet name from definition, falling back to standard name.");
			}
			exportSheet(sheet, sheetName);
			i++;
		}
    }

    public void exportSheet(XlsSheet sheetDefinition, String id) throws Exception
    {
		logger.debug("Creating Sheet " +id);
        // Create JXPath context for working with the report data
        context = JXPathContext.newContext(report);
        
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
				String columnId   = columnDefinition.getLangs().getLang().get(0).getTranslation();
				
				String expression = "";
				
				
				Boolean relative  = true;
                if (columnDefinition.getXlsTransformation().isSetBeanProperty())
				{
					expression = columnDefinition.getXlsTransformation().getBeanProperty();
				}
				else if (columnDefinition.getXlsTransformation().isSetXPath())
				{
					expression = columnDefinition.getXlsTransformation().getXPath();
					relative   = false;
				}
				
				if (sheetDefinition.isSetPrimaryKey())
				{
					String primaryKey = relativeContext.getValue(sheetDefinition.getPrimaryKey()).toString();
					expression = expression.replace("@@@primaryKey@@@", primaryKey);
				}
				
                
                try {
                    Object  value = null;
                    //String xpath  = query +"[" +row +"]/" + expression;
                    logger.trace("Using XPath expression: " +expression);
					if (relative)
					{
						value = relativeContext.getValue(expression);
						logger.trace("... in relative context.");
					}
					else
					{
						value = context.getValue(expression);
						logger.trace("... in complete context.");
					}
                    logger.trace("Got Value " +value.toString());
                    createCell(sheet, rowNr, i, value, type, style);

                } catch (Exception e)
                {
                    Integer counter = errors.get(columnId);
                    counter++;
                    errors.put(columnId, counter);
					logger.trace("ERROR occured: " +e.getMessage());
                }
            }
			
			rowNr++;
        }
        logger.info("Processed " +rowNr +" entries.");
        
        fixWidth(sortedColumns, sheet);
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
		Font font = wb.createFont();
		font.setFontName("Arial");
		style.setFont(font);
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

    public Sheet fixWidth(List<XlsColumn> columns, Sheet sheet)
    {
		
		for (int i = 0; i<columns.size(); i++)
            {
				String formatPattern = "";
				if (columns.get(i).getXlsTransformation().isSetFormatPattern())
				{
					formatPattern = columns.get(i).getXlsTransformation().getFormatPattern();
					logger.trace("Format Pattern " +formatPattern);
				}
				if (formatPattern.contains(" "))
				{
					sheet.setColumnWidth(i, MIN_WIDTH);
					logger.trace("Column " +i +" is to MID_WIDTH which is " +sheet.getColumnWidth(i));
				}
				else
				{
					sheet.autoSizeColumn(i);
					logger.trace("Column " +i +" is set of " +sheet.getColumnWidth(i));
				}
				
            }
        return sheet;
    }

    public ArrayList<XlsColumn> preProcessColumns(XlsSheet sheet)
    {
        ArrayList<XlsColumn> columns = new ArrayList<XlsColumn>();
		Integer columnNr = 0;
        for (Object o : sheet.getContent())
        {
			if (o instanceof XlsColumn)
			{
				XlsColumn c = (XlsColumn) o;
				c.setColumn("" +columnNr);
				columns.add(c);
				cellStyles.put("" +columnNr, getCellStyle(c));
				columnNr++;
			}
			if (o instanceof XlsMultiColumn)
			{
				XlsMultiColumn c = (XlsMultiColumn) o;
				List<XlsColumn> list = createColumns(c, columnNr);
				columns.addAll(list);
				columnNr = columnNr + list.size();
				//TODO Create Label row and shift all headers to 2nd row, begin iterating data in 3rd
				//Info about merging can be found here: http://poi.apache.org/spreadsheet/quick-guide.html#MergedCells
			}
        }
        return columns;
    }
	
	public List<XlsColumn> createColumns(XlsMultiColumn multiColumn, Integer startAt)
	{
		ArrayList<XlsColumn> list = new ArrayList<XlsColumn>();
		
		// Create Content Rows
		String queryExpression = multiColumn.getQuery();
		logger.info("Iterating to find the elements for the multi column " +queryExpression);
		
		Iterator iterator     = context.iteratePointers(queryExpression);
		logger.debug("Beginning iteration");
        
		// The XPath template to be filled with the ID from the multi column definition
		XlsColumn columnTemplate = multiColumn.getXlsColumn().get(0);
		String template = columnTemplate.getXlsTransformation().getBeanProperty();
		
		while (iterator.hasNext())
        {
			Pointer pointerToItem = (Pointer)iterator.next();
			logger.trace("Got pointer: " +pointerToItem.getValue().getClass());
			JXPathContext relativeContext = context.getRelativeContext(pointerToItem);
			
			Object  value = null;
			//String xpath  = query +"[" +row +"]/" + expression;

			String expressionId    = multiColumn.getId();
			String expressionLabel = multiColumn.getColumnLabel();

			logger.trace("Using XPath expression to evaluate the : " +expressionId);
			String id    = relativeContext.getValue(expressionId).toString();
			String label = relativeContext.getValue(expressionLabel).toString();
			logger.trace("Got Value " +id);

			String xPath = template.replace("@@@columnId@@@", id);
			logger.info(xPath);
			XlsColumn c = createColumn(startAt, xPath, label, columnTemplate);
			list.add(c);
			startAt++;
		}
		return list;
		
	}
		
	public XlsColumn createColumn(Integer columnNr, String xPath, String label, XlsColumn columnTemplate)
	{
		logger.trace("Creating Column at position " +columnNr + " with expression " +xPath + " and label " +label + " for language " +languageKey);
		XlsColumn c = new XlsColumn();

		XlsTransformation t = new XlsTransformation();
		t.setXPath(xPath);
		t.setFormatPattern(columnTemplate.getXlsTransformation().getFormatPattern());
		t.setDataClass(columnTemplate.getXlsTransformation().getDataClass());
		
		Langs langs = new Langs();
		Lang  lang  = new Lang();
		lang.setKey(languageKey);
		lang.setTranslation(label);
		langs.getLang().add(lang);
		
		c.setLangs(langs);
		c.setXlsTransformation(t);
		c.setColumn(columnNr +"");
		
		cellStyles.put("" +columnNr, getCellStyle(c));
		return c;
	}

    public Workbook getWb() {return wb;}
    public void setWb(Workbook wb) {this.wb = wb;}
}