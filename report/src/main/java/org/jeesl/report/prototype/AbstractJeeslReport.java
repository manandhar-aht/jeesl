package org.jeesl.report.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.xls.system.io.report.XlsFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportCellComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportRowComparator;
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
											CDT extends UtilsStatus<CDT,L,D>,
											CW extends UtilsStatus<CW,L,D>,
											RT extends UtilsStatus<RT,L,D>,
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
	
	private boolean showHeaderGroup; public boolean isShowHeaderGroup() {return showHeaderGroup;}
	private boolean showHeaderColumn; public boolean isShowHeaderColumn() {return showHeaderColumn;}
	
	protected List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	protected List<COLUMN> columns; public List<COLUMN> getColumns() {return columns;}
	protected Map<GROUP,Integer> mapGroupChilds; public Map<GROUP, Integer> getMapGroupChilds() {return mapGroupChilds;}

	protected REPORT ioReport; public REPORT getIoReport() {return ioReport;}
	protected SHEET ioSheet; public SHEET getIoSheet() {return ioSheet;}
	
	protected XlsFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> xlsFactory;
	
	protected JsonFlatFigures flats; public JsonFlatFigures getFlats() {return flats;}
	
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	private Comparator<COLUMN> comparatorColumn;
	private Comparator<ROW> comparatorRow;
	private Comparator<CELL> comparatorCell;

	public AbstractJeeslReport(final Class<REPORT> cReport, String localeCode)
	{
		this.cReport=cReport;
		this.localeCode=localeCode;
		debugOnInfo = false;
		
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportGroupComparator.Type.position);
		comparatorColumn = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportColumnComparator.Type.position);
		comparatorRow = new IoReportRowComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportRowComparator.Type.position);
		comparatorCell = new IoReportCellComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportCellComparator.Type.position);
		
		showHeaderGroup = true;
		buildHeaders();
	}
	
	protected void initIo(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport, Class<?> classReport, final Class<L> cL,final Class<D> cD, final Class<CATEGORY> cCategory, final Class<IMPLEMENTATION> cImplementation, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CELL> cCell, final Class<STYLE> cStyle, final Class<CDT> cDataType, final Class<CW> cColumnWidth)
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
						
						showHeaderGroup=false;
						showHeaderColumn=false;
						for(GROUP g : groups)
						{
							if(g.isVisible())
							{
								if(g.getShowLabel()){showHeaderGroup=true;}
								for(COLUMN c : g.getColumns())
								{
									if(c.isVisible() && c.getShowLabel())
									{
										{showHeaderColumn=true;}
									}
								}
							}
						}
						
						for(SHEET s : ioWorkbook.getSheets())
						{
							Collections.sort(s.getGroups(), comparatorGroup);
							Collections.sort(s.getRows(), comparatorRow);
							for(GROUP g : s.getGroups())
							{
								Collections.sort(g.getColumns(), comparatorColumn);
							}
							for(ROW r : s.getRows())
							{
								if(r.getTemplate()!=null)
								{
									r.setTemplate(fReport.load(r.getTemplate()));
									Collections.sort(r.getTemplate().getCells(),comparatorCell);
								}
							}
						}
						xlsFactory = new XlsFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>(localeCode,cL,cD,cCategory,cReport,cImplementation,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cStyle,cDataType,cColumnWidth,ioWorkbook);
					}
				}
			}
			catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
			
			if(debugOnInfo)
			{
				logger.info("Debugging: "+classReport.getSimpleName());
				logger.info("... showGroupings: "+showHeaderGroup);
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