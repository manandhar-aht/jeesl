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
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.report.excel.JeeslExcelDomainExporter;
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
											REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
											IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
											WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
											SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
											GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
											COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
											ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
											CDT extends UtilsStatus<CDT,L,D>,
											RO extends UtilsStatus<RO,L,D>,
											ENTITY extends EjbWithId,
											ATTRIBUTE extends EjbWithId,
											FILLING extends UtilsStatus<FILLING,L,D>,
											TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslReport.class);
	
	protected boolean debugOnInfo;
	
	private final Class<REPORT> cReport;
	protected final String localeCode;
	
	protected List<String> headers; public List<String> getHeaders() {return headers;}
	private boolean showGroupings; public boolean isShowGroupings() {return showGroupings;}
	
	protected List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	protected List<COLUMN> columns; public List<COLUMN> getColumns() {return columns;}
	protected Map<GROUP,Integer> mapGroupChilds; public Map<GROUP, Integer> getMapGroupChilds() {return mapGroupChilds;}

	protected REPORT ioReport; public REPORT getIoReport() {return ioReport;}
	protected SHEET ioSheet; public SHEET getIoSheet() {return ioSheet;}
	
	protected JeeslExcelDomainExporter<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xlsExporterDomain;
	
	protected JsonFlatFigures flats; public JsonFlatFigures getFlats() {return flats;}
	
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	private Comparator<COLUMN> comparatorColumn;

	public AbstractJeeslReport(final Class<REPORT> cReport, String localeCode)
	{
		this.cReport=cReport;
		this.localeCode=localeCode;
		debugOnInfo = false;
		
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup  = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportGroupComparator.Type.position);
		comparatorColumn  = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportColumnComparator.Type.position);
		
		showGroupings = true;
		buildHeaders();
	}
	
	protected void initIo(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport, Class<?> classReport, final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn)
	{
		if(fReport!=null)
		{
			try
			{
				ioReport = fReport.fByCode(cReport,classReport.getSimpleName());
				ioReport = fReport.load(ioReport,true);
				if(ioReport.getWorkbook()!=null)
				{
					WORKBOOK ioWorkbook = ioReport.getWorkbook();
					if(!ioWorkbook.getSheets().isEmpty())
					{
						Collections.sort(ioWorkbook.getSheets(), comparatorSheet);
						ioSheet = fReport.load(ioWorkbook.getSheets().get(0), true);
						
						mapGroupChilds = EjbIoReportColumnGroupFactory.toMapVisibleGroupSize(ioSheet);
						groups = EjbIoReportColumnGroupFactory.toListVisibleGroups(ioSheet);
						columns = EjbIoReportColumnFactory.toListVisibleColumns(ioSheet);
			
						Collections.sort(groups, comparatorGroup);
						Collections.sort(columns, comparatorColumn);
						
						showGroupings=false;
						for(GROUP g : groups){if(g.getShowLabel()){showGroupings=true;}}
						
						for(SHEET s : ioWorkbook.getSheets())
						{
							Collections.sort(s.getGroups(), comparatorGroup);
							for(GROUP g : s.getGroups())
							{
								Collections.sort(g.getColumns(), comparatorColumn);
							}
						}
						xlsExporterDomain = new JeeslExcelDomainExporter<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(localeCode,cL,cD,cReport,cWorkbook,cSheet,cGroup,cColumn,ioWorkbook);
					}
				}
			}
			catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
			
			if(debugOnInfo)
			{
				logger.info("Debugging: "+classReport.getSimpleName());
				logger.info("... showGroupings: "+showGroupings);
				for(GROUP g : groups)
				{
					logger.info("\t"+g.getClass().getSimpleName()+" ("+mapGroupChilds.get(g)+"): "+g.getName().get(localeCode).getLang());
					for(COLUMN c : columns)
					{
						if(c.getGroup().equals(g))
						{
							logger.info("\t\t"+c.getClass().getSimpleName()+": "+c.getName().get(localeCode).getLang());
						}
					}
				}
			}
		}
		else{logger.warn("Trying to super.initIo(), but "+JeeslIoReportFacade.class.getSimpleName()+" is null");}
	}
	
	private void buildHeaders()
	{
		headers = new ArrayList<String>();
	}
}