package org.jeesl.report.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportSheetComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractJeeslReport<L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
											WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
											SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
											GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
											COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
											FILLING extends UtilsStatus<FILLING,L,D>,
											TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslReport.class);
	
	private final Class<REPORT> cReport;
	protected final String localeCode;
	
	protected List<String> headers; public List<String> getHeaders() {return headers;}
	protected Map<GROUP,Integer> mapGroupChilds; public Map<GROUP, Integer> getMapGroupChilds() {return mapGroupChilds;}

	protected REPORT ioReport; public REPORT getIoReport() {return ioReport;}
	protected SHEET ioSheet; public SHEET getIoSheet() {return ioSheet;}
	
	protected JsonFlatFigures flats; public JsonFlatFigures getFlats() {return flats;}
	
	private Comparator<SHEET> comparatorSheet;

	public AbstractJeeslReport(final Class<REPORT> cReport, String localeCode)
	{
		this.cReport=cReport;
		this.localeCode=localeCode;
		
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>().factory(IoReportSheetComparator.Type.position);
		
		buildHeaders();
	}
	
	protected void initIo(JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport, Class<?> c)
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
					mapGroupChilds = EjbIoReportColumnGroupFactory.toMapVisibleGroupSize(ioSheet);
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