package org.jeesl.report.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportSheetComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractJeeslReport<L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
											IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
											WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
											SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
											GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
											COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
											CDT extends UtilsStatus<CDT,L,D>,
											ENTITY extends EjbWithId,
											ATTRIBUTE extends EjbWithId,
											FILLING extends UtilsStatus<FILLING,L,D>,
											TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslReport.class);
	
	private final Class<REPORT> cReport;
	protected final String localeCode;
	
	protected List<String> headers; public List<String> getHeaders() {return headers;}
	
	protected List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	protected List<COLUMN> columns; public List<COLUMN> getColumns() {return columns;}
	protected Map<GROUP,Integer> mapGroupChilds; public Map<GROUP, Integer> getMapGroupChilds() {return mapGroupChilds;}

	protected REPORT ioReport; public REPORT getIoReport() {return ioReport;}
	protected SHEET ioSheet; public SHEET getIoSheet() {return ioSheet;}
	
	protected JsonFlatFigures flats; public JsonFlatFigures getFlats() {return flats;}
	
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	private Comparator<COLUMN> comparatorColumn;

	public AbstractJeeslReport(final Class<REPORT> cReport, String localeCode)
	{
		this.cReport=cReport;
		this.localeCode=localeCode;
		
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup  = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportGroupComparator.Type.position);
		comparatorColumn  = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportColumnComparator.Type.position);
		
		buildHeaders();
	}
	
	protected void initIo(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport, Class<?> c)
	{
		if(fReport!=null)
		{
			try
			{
				ioReport = fReport.fByCode(cReport,c.getSimpleName());
				ioReport = fReport.load(ioReport,false);
				if(ioReport.getWorkbook()!=null && !ioReport.getWorkbook().getSheets().isEmpty())
				{
					Collections.sort(ioReport.getWorkbook().getSheets(), comparatorSheet);
					ioSheet = fReport.load(ioReport.getWorkbook().getSheets().get(0), true);
					Collections.sort(ioSheet.getGroups(), comparatorGroup);
					for(GROUP g : ioSheet.getGroups()){Collections.sort(g.getColumns(), comparatorColumn);}
					mapGroupChilds = EjbIoReportColumnGroupFactory.toMapVisibleGroupSize(ioSheet);
					groups = EjbIoReportColumnGroupFactory.toListVisibleGroups(ioSheet);
					columns = EjbIoReportColumnFactory.toListVisibleColumns(ioSheet);
		
					Collections.sort(groups, comparatorGroup);
					Collections.sort(columns, comparatorColumn);
				}
			}
			catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		}
	}
	
	private void buildHeaders()
	{
		headers = new ArrayList<String>();
	}
}