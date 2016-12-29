package org.jeesl.factory.xls.system.io.report;

import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class XlsCellStyleProvider<L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XlsCellStyleProvider.class);
		
	private CellStyle styleFallback; public CellStyle getStyleFallback() {return styleFallback;}
	
	private CellStyle styleLabelCenter; public CellStyle getStyleLabelCenter() {return styleLabelCenter;}
	private CellStyle styleLabelLeft; public CellStyle getStyleLabelLeft() {return styleLabelLeft;}

	public XlsCellStyleProvider(Workbook xlsWorkbook, List<COLUMN> ioColumns, List<ROW> ioRows)
	{
        Font fontItalicBold = xlsWorkbook.createFont();
        fontItalicBold.setItalic(true);
        fontItalicBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		styleFallback = xlsWorkbook.createCellStyle();
//        dateHeaderStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
        styleFallback.setAlignment(CellStyle.ALIGN_LEFT);
        styleFallback.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
 //       dateHeaderStyle.setFont(font);
        
        styleLabelCenter = xlsWorkbook.createCellStyle();
        styleLabelCenter.setAlignment(CellStyle.ALIGN_CENTER);
        styleLabelCenter.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        styleLabelCenter.setFont(fontItalicBold);
        
        styleLabelLeft = xlsWorkbook.createCellStyle();
        styleLabelLeft.setAlignment(CellStyle.ALIGN_LEFT);
        styleLabelLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        styleLabelLeft.setFont(fontItalicBold);
	}
	
	public CellStyle get(COLUMN column)
	{
		return styleFallback;
	}
	
	public CellStyle get(ROW row)
	{
		return styleFallback;
	}
}