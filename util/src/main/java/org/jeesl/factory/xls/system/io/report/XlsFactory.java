package org.jeesl.factory.xls.system.io.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jeesl.factory.builder.system.ReportFactoryBuilder;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.pojo.system.io.report.JeeslTreeFigureFactory;
import org.jeesl.interfaces.controller.report.JeeslReport;
import org.jeesl.interfaces.controller.report.JeeslReportSelectorTransformation;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportRowType;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportSetting;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class XlsFactory <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
										IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
										WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
										SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
										GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
										COLUMN extends JeeslReportColumn<L,D,GROUP,STYLE,CDT,CW,TLS>,
										ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
										TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
										CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
										STYLE extends JeeslReportStyle<L,D>,
										CDT extends UtilsStatus<CDT,L,D>,
										CW extends UtilsStatus<CW,L,D>,
										RT extends UtilsStatus<RT,L,D>,
										RCAT extends UtilsStatus<RCAT,L,D>,
										ENTITY extends EjbWithId,
										ATTRIBUTE extends EjbWithId,
										TL extends JeeslTrafficLight<L,D,TLS>,
										TLS extends UtilsStatus<TLS,L,D>,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	private final static Logger logger = LoggerFactory.getLogger(XlsFactory.class);
		
	private WORKBOOK ioWorkbook;
	
	private final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RCAT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport;
	private final EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,?,?> efColumn;
	private final EjbIoReportRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efRow;
	
	private EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efSheet;
	
    // Excel related objects
    public Font             headerFont;
    public CellStyle        dateHeaderStyle;
    public CellStyle        numberStyle;
    public CreationHelper   createHelper;
    
    // How many results are there for the given query
    public Integer   counter;
	
	// Current line while exporting
	
	private String localeCode;
        
    public Hashtable<String, CellStyle> cellStyles = new Hashtable<String, CellStyle>();
    public Hashtable<String, Integer> errors = new Hashtable<String, Integer>();
	
	public XlsFactory(String localeCode,
			final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RCAT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport,
			WORKBOOK ioWorkbook)
    {
        this.localeCode = localeCode;
        this.ioWorkbook=ioWorkbook;
        this.fbReport=fbReport;
        efColumn = fbReport.column();
        efSheet = fbReport.sheet();
        efRow = fbReport.row();
    }
	
	private void init(Workbook wb)
	{
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
	}
	
	public void write(Object report, OutputStream os) throws IOException {write(null,report,os);}
	public void write(JeeslReport<REPORT> jeeslReport, Object report, OutputStream os) throws IOException
	{
		Map<SHEET,Boolean> mapSheetVisibilityToggle = null; 
		
//	    Workbook wb = new XSSFWorkbook();
	    SXSSFWorkbook wb = new SXSSFWorkbook(100);
	    init(wb);
	    
	    JXPathContext context = JXPathContext.newContext(report);
	    
		for(SHEET ioSheet : EjbIoReportSheetFactory.toListVisibleShets(ioWorkbook,mapSheetVisibilityToggle))
		{
			logger.info("Processing sheet: "+ioSheet.getCode());
			List<GROUP> groups = EjbIoReportColumnGroupFactory.toListVisibleGroups(ioSheet);
			List<COLUMN> columns = efColumn.toListVisibleColumns(ioSheet);
			List<ROW> rows = efRow.toListVisibleRows(ioSheet);
			
			XlsStyleFactory<SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfStyle = fbReport.xlsStyle(wb,groups,columns,rows);
			XlsCellFactory<REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfCell = fbReport.xlsCell(localeCode,xfStyle);
			XlsRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfRow = fbReport.xlsRow(localeCode,xfCell);
			XlsColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfColumn = fbReport.xlsColumn();
			
			MutableInt rowNr = new MutableInt(0);
			String sheetName = ioSheet.getName().get(localeCode).getLang();
			Sheet sheet = XlsSheetFactory.getSheet(wb,sheetName);
			xfColumn.trackWidth(sheet, columns);
			
			for(ROW ioRow : rows)
			{
//				logger.info(ioRow.getPosition()+" "+ioRow.getName().get(localeCode).getLang());
				switch(JeeslReportRowType.Code.valueOf(ioRow.getType().getCode()))
				{
					case label: xfRow.label(sheet, rowNr, ioRow); break;
					case labelValue: xfRow.labelValue(sheet, rowNr, ioRow, context); break;
					case table: applyTable(jeeslReport,context,sheet,rowNr,ioSheet,ioRow,columns,xfRow,xfCell); break;
					case template: applyTemplate(sheet,rowNr,ioSheet,ioRow,xfCell); break;
					default: break;
				}
			}
			xfColumn.adjustWidth(sheet, columns);
			
//			applyHeader(sheet,ioSheet,report);
//			exportSheet(sheet,report,ioSheet,null);
//			applyFooter(sheet,report);
			rowNr.add(3);
		}
		wb.write(os);
		wb.dispose();
	}

	@SuppressWarnings("unchecked")
	private void applyTable(JeeslReport<REPORT> jeeslReport, JXPathContext context, Sheet sheet, MutableInt rowNr, SHEET ioSheet, ROW ioRow, List<COLUMN> columns, XlsRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xlfRow, XlsCellFactory<REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfCell)
	{
		rowNr.add(ioRow.getOffsetRows());
		JeeslReportSetting.Implementation implementation = JeeslReportSetting.Implementation.valueOf(ioSheet.getImplementation().getCode());
		JeeslReportSetting.Transformation transformation = JeeslReportSetting.Transformation.none;
		
		if(jeeslReport instanceof JeeslReportSelectorTransformation)
		{
			JeeslReportSelectorTransformation<L,D,REPORT,TRANSFORMATION> jr = ((JeeslReportSelectorTransformation<L,D,REPORT,TRANSFORMATION>)jeeslReport);
			transformation = JeeslReportSetting.Transformation.valueOf(jr.getReportSettingTransformation().getCode());
		}
		logger.info("Tranformation:"+transformation+" instanceof:"+(jeeslReport instanceof JeeslReportSelectorTransformation));
		
		Figures tree = null;
		Figures treeHeader = null;
		Figures transformationHeader = null;
		try
		{
			switch(implementation)
			{
				case tree: tree = (Figures)context.getValue(ioSheet.getQueryTable());
						   treeHeader = ReportXpath.getFigures(JeeslTreeFigureFactory.Type.tree, tree.getFigures());
						   if(transformation.equals(JeeslReportSetting.Transformation.last)){transformationHeader = ReportXpath.getFigures(JeeslTreeFigureFactory.Type.transformation, tree.getFigures());}
						   break;
				default: break;
			}
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		
		switch(implementation)
		{
			case model: xlfRow.header(sheet,rowNr,ioSheet);break;
			case flat: xlfRow.header(sheet,rowNr,ioSheet);break;
			case tree: xlfRow.headerTree(sheet,rowNr,ioSheet,treeHeader,transformation,transformationHeader);break;
		}
		
		switch(implementation)
		{
			case model: applyDomainTable(context,sheet,rowNr,ioSheet,columns,xfCell);break;
			case flat: applyDomainTable(context,sheet,rowNr,ioSheet,columns,xfCell);break;
			case tree: applyTreeTable(tree,treeHeader,sheet,rowNr,ioSheet,columns,xfCell,transformation);break;
		}

        if(efSheet.hasFooters(ioSheet))
        {
        	Row xlsFooter = sheet.createRow(rowNr.intValue());
        	logger.info(StringUtil.stars());
        	logger.info("Handling Footer");
        	MutableInt columnNr = new MutableInt(0);
        	for(COLUMN ioColumn : efColumn.toListVisibleColumns(ioSheet))
			{
				if(efColumn.hasFooter(ioColumn))
				{
					xfCell.footer(ioColumn,xlsFooter,columnNr,context);
				}
				else
				{
					columnNr.add(1);
				}
			}
        	rowNr.add(1);
        }
	}
	
	private void applyDomainTable(JXPathContext context, Sheet sheet, MutableInt rowNr, SHEET ioSheet, List<COLUMN> columns, XlsCellFactory<REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfCell)
	{
		logger.info("Applying Domain Table for "+ioSheet.getQueryTable());
		
		@SuppressWarnings("unchecked")
		Iterator<Pointer> iterator = context.iteratePointers(ioSheet.getQueryTable());
		logger.info("Beginning iteration");
		int i=0;
        while (iterator.hasNext())
        {
        	i++;
//        	logger.info("Row "+i);
        	Row xlsRow = sheet.createRow(rowNr.intValue());
        	
            Pointer pointer = iterator.next();
			if (logger.isTraceEnabled()) {logger.info("Got pointer: " +pointer.getValue().getClass());}
			JXPathContext relativeContext = context.getRelativeContext(pointer);
			
			MutableInt columnNr = new MutableInt(0);
			for(COLUMN ioColumn : columns)
			{
//				logger.info(ioColumn.getPosition()+" "+columnNr.intValue());
				xfCell.cell(ioColumn,xlsRow,columnNr,relativeContext);
			}
			rowNr.add(1);
        }
	}
	
	private void applyTreeTable(Figures tree, Figures treeHeader, Sheet sheet, MutableInt rowNr, SHEET ioSheet, List<COLUMN> columns, XlsCellFactory<REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfCell, JeeslReportSetting.Transformation transformation)
	{
		try
		{
			Figures data = ReportXpath.getFigures(JeeslTreeFigureFactory.Type.data, tree.getFigures());
			for(Figures f : data.getFigures())
			{
				applyTreeRow(0,treeHeader,sheet,rowNr,columns,xfCell,new ArrayList<String>(),f,transformation);
			}
			JaxbUtil.trace(data);
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
	}
	
	private void applyTreeRow(int level, Figures treeHeader, Sheet sheet, MutableInt rowNr, List<COLUMN> columns, XlsCellFactory<REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfCell, List<String> parents, Figures f, JeeslReportSetting.Transformation transformation)
	{
		MutableInt columnNr = new MutableInt(0);
		columnNr.add(level);
		Row xlsRow = sheet.createRow(rowNr.intValue());
		boolean treeColumn=true;
		for(COLUMN ioColumn : columns)
		{
			if(treeColumn)
			{
				xfCell.cell(ioColumn,xlsRow,columnNr.intValue(),f.getLabel());
				treeColumn=false;
				List<String> path = new ArrayList<String>(parents);
				path.add(f.getLabel());
				rowNr.add(1);
				for(Figures childs : f.getFigures())
				{
					applyTreeRow(level+1,treeHeader,sheet,rowNr,columns,xfCell,path,childs,transformation);
				}
				columnNr.add(treeHeader.getFigures().size()-level);
			}
			else
			{
				if(ioColumn.getQueryCell().startsWith("d"))
				{
					int nr = Integer.valueOf(ioColumn.getQueryCell().substring(1));
					switch(transformation)
					{
						case none: xfCell.cell(ioColumn,xlsRow,columnNr,Double.valueOf(f.getFinance().get(nr-1).getValue()));break;
						case last: for(Finance f2 : f.getFinance().get(nr-1).getFinance())
									{
										if(f2.isSetValue()){xfCell.cell(ioColumn,xlsRow,columnNr,f2.getValue());}
										else{columnNr.add(1);}
									}break;
						
					}
					
				}
				else{logger.warn("NYI: "+ioColumn.getQueryCell());}
			}
		}
		
	}
	
	private void applyTemplate(Sheet sheet, MutableInt rowNr, SHEET ioSheet, ROW ioRow, XlsCellFactory<REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfCell)
	{
		if(ioRow.getTemplate()!=null)
		{
			rowNr.add(ioRow.getOffsetRows());
			xfCell.build(sheet, rowNr, ioRow);
		}
	}
}