package org.jeesl.report.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellStyleFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellStyleProvider;
import org.jeesl.factory.xls.system.io.report.XlsRowFactory;
import org.jeesl.factory.xls.system.io.report.XlsSheetFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportRowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Label;
import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.report.XlsMultiColumn;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsTransformation;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;

public class JeeslExcelDomainExporter <L extends UtilsLang,D extends UtilsDescription,
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
										ATTRIBUTE extends EjbWithId,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	private final static Logger logger = LoggerFactory.getLogger(JeeslExcelDomainExporter.class);
	
	private WORKBOOK ioWorkbook;
	
	private ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> ffReport;
	
    // Excel related objects
    public Font             headerFont;
    public CellStyle        dateHeaderStyle;
    public CellStyle        numberStyle;
    private CellStyle styleLabel,styleFallback;
    public CreationHelper   createHelper;
    public JXPathContext	context;
    
    // How many results are there for the given query
    public Integer   counter;
	
	// Current line while exporting
	
	// Languge
	private String   localeCode;
        
    public Hashtable<String, CellStyle> cellStyles = new Hashtable<String, CellStyle>();
    public Hashtable<String, Integer> errors = new Hashtable<String, Integer>();
	
	private int MIN_WIDTH = 5000;
	
	public JeeslExcelDomainExporter(String localeCode, final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, WORKBOOK ioWorkbook)
    {
        this.localeCode = localeCode;
        this.ioWorkbook=ioWorkbook;
        
        ffReport = ReportFactoryFactory.factory(cL,cD,cReport,cWorkbook,cSheet,cGroup,cColumn,cRow);
    }
	
	private void init(Workbook wb)
	{
		createHelper = wb.getCreationHelper();

        // Create a new font and alter it.
        Font font = wb.createFont();
        font.setItalic(true);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        styleLabel = XlsCellStyleFactory.label(wb, font);
        styleFallback = XlsCellStyleFactory.fallback(wb);
        
        // Create styles
        dateHeaderStyle = wb.createCellStyle();
        dateHeaderStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
        dateHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        dateHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateHeaderStyle.setFont(font);

        numberStyle = wb.createCellStyle();
        numberStyle.setDataFormat(createHelper.createDataFormat().getFormat("#.00\\ RWF"));
	}
	
	public void write(Object report, OutputStream os) throws IOException
	{
	    Workbook wb = new XSSFWorkbook();
	    init(wb);
	    
	    JXPathContext context = JXPathContext.newContext(report);
	    
		for(SHEET ioSheet : ioWorkbook.getSheets())
		{
			List<COLUMN> columns = EjbIoReportColumnFactory.toListVisibleColumns(ioSheet);
			List<ROW> rows = EjbIoReportRowFactory.toListVisibleRows(ioSheet);
			
			XlsCellStyleProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> csp = ffReport.xlsCellStyleProvider(wb,columns,rows);
			XlsCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xfCell = ffReport.xlsCell(localeCode,csp);
			XlsRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xfRow = ffReport.xlsRow(localeCode,xfCell);
			
			MutableInt rowNr = new MutableInt(1);
			String sheetName = ioSheet.getName().get(localeCode).getLang();
			Sheet sheet = XlsSheetFactory.getSheet(wb,sheetName);
			
			for(ROW ioRow : rows)
			{
				logger.info(ioRow.getPosition()+" "+ioRow.getName().get(localeCode).getLang());
				switch(JeeslReportRowType.Code.valueOf(ioRow.getType().getCode()))
				{
					case label: xfRow.label(sheet, rowNr, ioRow); break;
					case labelValue: xfRow.labelValue(sheet, rowNr, ioRow, context); break;
					case table: applyTable(wb,context,sheet,rowNr,ioSheet,ioRow,columns,xfRow,xfCell); break;
					default: break;
				}
			}
			
//			applyHeader(sheet,ioSheet,report);
//			exportSheet(sheet,report,ioSheet,null);
//			applyFooter(sheet,report);
			rowNr.add(3);
		}
		wb.write(os);
	}
	
	private void applyTable(Workbook xslWorkbook, JXPathContext context, Sheet sheet, MutableInt rowNr, SHEET ioSheet, ROW ioRow, List<COLUMN> columns, XlsRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xlfRow, XlsCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE> xfCell)
	{
		rowNr.add(ioRow.getOffsetRows());
		xlfRow.header(sheet,rowNr,dateHeaderStyle,ioSheet);
		
		Iterator<Pointer> iterator = context.iteratePointers(ioSheet.getQueryTable());
		logger.debug("Beginning iteration");
        while (iterator.hasNext())
        {
        	Row xlsRow = sheet.createRow(rowNr.intValue());
        	
            Pointer pointerToItem = iterator.next();
			if (logger.isTraceEnabled()) {logger.info("Got pointer: " +pointerToItem.getValue().getClass());}
			JXPathContext relativeContext = context.getRelativeContext(pointerToItem);
			
			MutableInt columnNr = new MutableInt(0);
			for(COLUMN ioColumn : columns)
			{
				xfCell.build(ioColumn,xlsRow,columnNr,relativeContext);
			}
			
			rowNr.add(1);
        }
	}
		
	private void applyFooter(Workbook wb, Sheet sheet, MutableInt rowNr, Object report)
	{
		// Reset the context back to the complete report XML, because it might have been changed to a local one
		context = JXPathContext.newContext(report);
		
		// Get the Excel Sheet

		// Create the standard text style
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Arial");
		style.setFont(font);

		// Ask for all labels and add the ones starting with signature to a list
		Iterator iterator     = context.iteratePointers("/info/labels/label[@scope='signatures']");
		ArrayList<Label> signatureLabels = new ArrayList<Label>();
		while (iterator.hasNext())
		{
			Pointer pointerToItem = (Pointer)iterator.next();
			Object o = pointerToItem.getValue();
			if ((o!=null))
			{
				//TODO Finds also label='xy' attributes, must be restricted in XPath if possible for performance improvement
				if (logger.isTraceEnabled()) {logger.trace("Got pointer: " +o);}
				if (o.getClass() == Label.class)
				{
					Label l = (Label) pointerToItem.getValue();
					signatureLabels.add(l);
				}
			}
		}
		logger.info("Footer will be printed with " +signatureLabels.size() +" labels.");
		
		// Write the groups of label (e.g. approved by, prepared by) and the line to write the associated person
		Integer columnNr = 0;
		for (int i=0; i<signatureLabels.size();i++)
		{
			String responsible = "___________________";
			Label label = signatureLabels.get(i);
			if (label.isSetValue()) {responsible = label.getValue();}
			createCell(sheet, rowNr.intValue(),   columnNr, label.getKey(), "String", style);
			rowNr.add(1);createCell(sheet, rowNr.intValue(), columnNr, responsible, "String", style);
			rowNr.add(1);createCell(sheet, rowNr.intValue(), columnNr, "Date: ___/___/_____", "String", style);
			columnNr = columnNr + 2;
		}
	}
	
	// Maybe add a bunch of sheets here for grouped report
	// Introduce Offsets for iteration of columns
	// Possible data structure:
	// 
    private void exportSheet(Workbook wb, Sheet xlsSheet, MutableInt rowNr, Object report, SHEET ioSheet, XlsSheet sheetDefinition)
    {
        // Create JXPath context for working with the report data
        context = JXPathContext.newContext(report);
        
        // PreProcess columns to create Styles and count the number of results for the given report query
        ArrayList<XlsColumn> sortedColumns = preProcessColumns(wb,sheetDefinition,rowNr,xlsSheet);
		
        // Create Content Rows
		String queryExpression = sheetDefinition.getQuery();
		if (logger.isTraceEnabled()) {logger.trace("Iterating to find " +queryExpression);}
		Iterator iterator     = context.iteratePointers(queryExpression);
		logger.debug("Beginning iteration");
        while (iterator.hasNext())
        {
            Pointer pointerToItem = (Pointer)iterator.next();
			if (logger.isTraceEnabled()) {logger.trace("Got pointer: " +pointerToItem.getValue().getClass());}
			JXPathContext relativeContext = context.getRelativeContext(pointerToItem);
			
			for (int i = 0; i<sortedColumns.size(); i++)
            {
                XlsColumn columnDefinition = sortedColumns.get(i);
                CellStyle   style = cellStyles.get(columnDefinition.getColumn());
                String      type  = columnDefinition.getXlsTransformation().getDataClass();
				String columnId   = columnDefinition.getLangs().getLang().get(0).getTranslation();
				
				String expression = "";
				
				Boolean relative  = true;
				Boolean isJoin    = false;
				if (columnDefinition.getXlsTransformation().isSetBeanProperty())
				{
					expression = columnDefinition.getXlsTransformation().getBeanProperty();
					if (expression.startsWith("string-join")) {logger.info("Polyfill for XPath 2 string-join activated for " +expression);isJoin = true;}
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
                
                try
                {
                    Object  value;
                    //String xpath  = query +"[" +row +"]/" + expression;
                    if (logger.isTraceEnabled()) {logger.trace("Using XPath expression: " +expression);}
					if (relative && !isJoin)
					{
						value = relativeContext.getValue(expression);
						if (logger.isTraceEnabled()) {logger.trace("... in relative context.");}
					}
					else if (!relative && !isJoin)
					{
						value = context.getValue(expression);
						Iterator completeIterator = context.iterate(expression);
						if (completeIterator.hasNext())
						{ 
							value = completeIterator.next();
							if(logger.isTraceEnabled()) {logger.trace("Found " +value.toString());}
						}
						else {if(logger.isTraceEnabled()) {logger.trace("Could not find " +expression);}}
						if (logger.isTraceEnabled()) {logger.trace("... in complete context.");}
					}
					else
					{
						List<String> subSet = new ArrayList<String>();
						String joinExpression = expression.substring(expression.indexOf("(")+1, expression.indexOf(","));
						Iterator joinIterator = relativeContext.iteratePointers(joinExpression);
						while (joinIterator.hasNext())
						{
							Pointer pointerToSubItem	= (Pointer)joinIterator.next();
							String subValue				= pointerToSubItem.getValue().toString();
							subValue					= StringUtils.trim(subValue);
							subSet.add(subValue);
						}
						value = StringUtils.join(subSet, ", ");
					}
                    if (logger.isTraceEnabled()) {logger.trace("Got Value " +value.toString());}
                    createCell(xlsSheet, rowNr.intValue(), i, value, type, style);

                }
                catch (Exception e)
                {
					if (logger.isTraceEnabled()) {logger.trace("ERROR occured: " +e.getMessage());}
                }
            }
			
			rowNr.add(1);
        }
        logger.info("Processed " +rowNr.intValue() +" entries.");
        
        fixWidth(sortedColumns, xlsSheet);
        for (String key : errors.keySet())
        {
            if (errors.get(key)>0)
            {
                logger.error(errors.get(key) +" cell creation errors in " +key);
            }
        }
    }

    public CellStyle getCellStyle(Workbook wb, XlsColumn columnDefinition)
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
		if (dataClass.equalsIgnoreCase("Date"))
		{
			// Create styles
			style.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy"));
			if (columnDefinition.getXlsTransformation().isSetFormatPattern())
			{
				style.setDataFormat(createHelper.createDataFormat().getFormat(columnDefinition.getXlsTransformation().getFormatPattern()));
			}
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
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

    public Sheet createCell(Sheet sheet, Integer rowNr, Integer cellNr, Object value, String type, CellStyle style)
    {
		if (logger.isTraceEnabled()) {logger.trace("Cell at " +rowNr +"/" +cellNr +" Value: " +value.toString());}
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
		//	logger.info("Class " +value.toString());
												if (value.getClass().getSimpleName().equals("Long"))
												{
													Long l = (Long) value;
													cell.setCellValue(l.intValue());
												//	logger.info("Setting to Long " +l.intValue());
												}
												else if (value.getClass().getSimpleName().equals("int") || value.getClass().getSimpleName().equals("Integer"))
												{
													Integer i = (Integer) value;
													cell.setCellValue(i);
												//	logger.info("Setting to Integer " +i);
												}
												else
												{
                                                    cell.setCellValue(Integer.parseInt("" +value));
                                                }
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

    private ArrayList<XlsColumn> preProcessColumns(Workbook wb, XlsSheet sheet, MutableInt rowNr, Sheet xlsSheet)
    {
        ArrayList<XlsColumn> columns = new ArrayList<XlsColumn>();
		Integer columnNr = 0;
		boolean hasExtraHeader = false;
		Row     headerRow = null;
        for (Object o : sheet.getContent())
        {
			if (o instanceof XlsColumn)
			{
				XlsColumn c = (XlsColumn) o;
				c.setColumn("" +columnNr);
				columns.add(c);
				cellStyles.put("" +columnNr, getCellStyle(wb,c));
				columnNr++;
			}
			if (o instanceof XlsMultiColumn)
			{
				if (xlsSheet.getRow(rowNr.intValue()) == null)
				{
					headerRow = xlsSheet.createRow(rowNr.intValue());
				}
				else
				{
					headerRow = xlsSheet.getRow(rowNr.intValue());
				}
				XlsMultiColumn c = (XlsMultiColumn) o;
				List<XlsColumn> list = createColumns(wb,c, columnNr);
				columns.addAll(list);
				hasExtraHeader = true;
				
				Cell cell = headerRow.createCell(columnNr);
				cell.setCellStyle(dateHeaderStyle);
				cell.setCellValue(c.getLangs().getLang().get(0).getTranslation());
				for (int s = columnNr+1 ; s < columnNr + list.size() ; s++)
				{
					headerRow.createCell(s);
				}
			
				xlsSheet.addMergedRegion(new CellRangeAddress(rowNr.intValue(), rowNr.intValue(), columnNr, (short) (columnNr+list.size()-1)));
				columnNr = columnNr + list.size();
			}
        }
		if (hasExtraHeader) {rowNr.add(1);}
        return columns;
    }
	
	public List<XlsColumn> createColumns(Workbook wb, XlsMultiColumn multiColumn, Integer startAt)
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
			XlsColumn c = createColumn(wb,startAt, xPath, label, columnTemplate);
			list.add(c);
			startAt++;
		}
		return list;
		
	}
		
	public XlsColumn createColumn(Workbook wb, Integer columnNr, String xPath, String label, XlsColumn columnTemplate)
	{
		logger.trace("Creating Column at position " +columnNr + " with expression " +xPath + " and label " +label + " for language " +localeCode);
		XlsColumn c = new XlsColumn();

		XlsTransformation t = new XlsTransformation();
		t.setXPath(xPath);
		t.setFormatPattern(columnTemplate.getXlsTransformation().getFormatPattern());
		t.setDataClass(columnTemplate.getXlsTransformation().getDataClass());
		
		Langs langs = new Langs();
		Lang  lang  = new Lang();
		lang.setKey(localeCode);
		lang.setTranslation(label);
		langs.getLang().add(lang);
		
		c.setLangs(langs);
		c.setXlsTransformation(t);
		c.setColumn(columnNr +"");
		
		cellStyles.put("" +columnNr, getCellStyle(wb,c));
		return c;
	}
}