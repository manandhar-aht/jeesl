package org.jeesl.factory.xls.system.io.report;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class XlsColumnFactory <L extends UtilsLang,D extends UtilsDescription,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
							WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
							TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
							RT extends UtilsStatus<RT,L,D>,
							ENTITY extends EjbWithId,
							ATTRIBUTE extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(XlsColumnFactory.class);
		
	public XlsColumnFactory()
	{
		
	}
	
	public void adjustWidth(Sheet sheet, List<COLUMN> columns)
    {
		for(int i=0; i<columns.size(); i++)
        {
			COLUMN ioColumn = columns.get(i);
			if(ioColumn.getColumWidth()!=null)
			{
				switch(JeeslReportLayout.ColumnWidth.valueOf(ioColumn.getColumWidth().getCode()))
				{
					case none: break;
					case auto: sheet.autoSizeColumn(i);break;
					case min: sheet.setColumnWidth(i, ioColumn.getColumSize());break;
					default: break;
				}
			}
        }
    }
}