package org.jeesl.factory.xml.system.io.report;

import java.util.Collections;
import java.util.Comparator;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportRowComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Row;
import net.sf.ahtutils.xml.report.Rows;

public class XmlRowsFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,
								CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRowsFactory.class);
	
	private Rows q;
	
	private Comparator<ROW> cRow;
	
	private XmlRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xfRow;
	
	public XmlRowsFactory(String localeCode, Rows q)
	{
		this.q=q;
		cRow = new IoReportRowComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportRowComparator.Type.position);
		if(q.isSetRow()){xfRow = new XmlRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(localeCode,q.getRow().get(0));}
	}
	
	public Rows build(SHEET sheet)
	{
		Rows xml = new Rows();
				
		if(q.isSetRow())
		{
			Collections.sort(sheet.getRows(),cRow);
			for(ROW row : sheet.getRows())
			{
				xml.getRow().add(xfRow.build(row));
			}
		}
		
		return xml;
	}
	
	public static Rows build(Row row)
	{
		Rows xml = new Rows();		
		xml.getRow().add(row);
		return xml;
	}
}