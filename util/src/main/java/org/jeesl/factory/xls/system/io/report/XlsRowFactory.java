package org.jeesl.factory.xls.system.io.report;

import java.util.Map;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
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

public class XlsRowFactory <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XlsRowFactory.class);
		
	private String localeCode;
	
	public XlsRowFactory(String localeCode)
	{
		this.localeCode = localeCode;
	}
	
	public void header(Sheet sheet, MutableInt rowNr, CellStyle dateHeaderStyle, SHEET ioSheet)
    {
		Map<GROUP,Integer> mapSize = EjbIoReportColumnGroupFactory.toMapVisibleGroupSize(ioSheet);

		Row groupingRow = sheet.createRow(rowNr.intValue());
		int columnNr = 0;
		for(GROUP g : EjbIoReportColumnGroupFactory.toListVisibleGroups(ioSheet))
		{
			Cell cell = groupingRow.createCell(columnNr);
            cell.setCellStyle(dateHeaderStyle);
            cell.setCellValue(g.getName().get(localeCode).getLang());
            if(mapSize.get(g)>1)
            {
            	sheet.addMergedRegion(new CellRangeAddress(rowNr.intValue(), rowNr.intValue(), columnNr, columnNr+mapSize.get(g)-1));
            	columnNr = columnNr + mapSize.get(g);
            }
          else{columnNr++;}
		}
		rowNr.add(1);
		
		Row headerRow = sheet.createRow(rowNr.intValue());
		columnNr = 0;
		for(COLUMN c : EjbIoReportColumnFactory.toListVisibleColumns(ioSheet))
		{
			Cell cell = headerRow.createCell(columnNr);
            cell.setCellStyle(dateHeaderStyle);
            cell.setCellValue(c.getName().get(localeCode).getLang());
            columnNr++;
		}
		rowNr.add(1);
    }
	
	public static void header(Sheet sheet, MutableInt rowNr, CellStyle dateHeaderStyle, String[] headers)
    {
		header(sheet, rowNr.intValue(), dateHeaderStyle, headers);
		rowNr.add(1);
    }
	
	public static void header(Sheet sheet, int rowNr, CellStyle dateHeaderStyle, String[] headers)
    {
        Row     headerRow = sheet.createRow(rowNr);
        Integer cellNr = 0;
        for (String header : headers)
        {
			Cell cell = headerRow.createCell(cellNr);
                            cell.setCellStyle(dateHeaderStyle);
                            cell.setCellValue(header);
            cellNr++;
        }
    }
}