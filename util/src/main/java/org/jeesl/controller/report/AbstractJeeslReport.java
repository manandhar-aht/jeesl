package org.jeesl.controller.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.controller.processor.JobCodeProcessor;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.factory.xls.system.io.report.XlsFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportSetting;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportCellComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportRowComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportSheetComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.io.JsonUtil;
import net.sf.exlp.util.io.StringUtil;

public abstract class AbstractJeeslReport<L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
											WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											STYLE extends JeeslReportStyle<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
											CDT extends UtilsStatus<CDT,L,D>,
											CW extends UtilsStatus<CW,L,D>,
											RT extends UtilsStatus<RT,L,D>,
											ENTITY extends EjbWithId,
											ATTRIBUTE extends EjbWithId,
											TL extends JeeslTrafficLight<L,D,TLS>,
											TLS extends UtilsStatus<TLS,L,D>,
											FILLING extends UtilsStatus<FILLING,L,D>,
											TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslReport.class);
	
	protected boolean debugOnInfo;
	protected boolean developmentMode; public void activateDevelopmenetMode() {developmentMode=true;}
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<REPORT> cReport;
	private final Class<CATEGORY> cCategory;

	protected String localeCode;
	protected String jobCode;
	protected String jobName;
	
	protected List<String> headers; public List<String> getHeaders() {return headers;}
	
	private boolean showHeaderGroup; public boolean isShowHeaderGroup() {return showHeaderGroup;}
	private boolean showHeaderColumn; public boolean isShowHeaderColumn() {return showHeaderColumn;}
	
	protected List<GROUP> groupsAll; public List<GROUP> getGroupsAll() {return groupsAll;}
	protected List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	protected List<COLUMN> columns; public List<COLUMN> getColumns() {return columns;}
	protected Map<GROUP,Integer> mapGroupChilds; public Map<GROUP,Integer> getMapGroupChilds() {return mapGroupChilds;}
	protected Map<GROUP,List<COLUMN>> mapGroupColumns; public Map<GROUP,List<COLUMN>> getMapGroupColumns() {return mapGroupColumns;}
	protected Map<GROUP,Boolean> mapGroupVisibilityToggle; public Map<GROUP, Boolean> getMapGroupVisibilityToggle() {return mapGroupVisibilityToggle;}

	protected REPORT ioReport; public REPORT getIoReport() {return ioReport;}
	protected SHEET ioSheet; public SHEET getIoSheet() {return ioSheet;}

	protected FILLING reportFilling;
	protected TRANSFORMATION reportSettingTransformation; public TRANSFORMATION getReportSettingTransformation() {return reportSettingTransformation;}

	protected EjbLangFactory<L> efLang;
	protected ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> ffReport;
	protected EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efColumn;
	protected XlsFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> xlsFactory;
	
	protected JeeslComparatorProvider<EjbWithId> cProvider;
	protected final JobCodeProcessor jobCodeProcessor;
	
	protected JsonFlatFigures flats; public JsonFlatFigures getFlats() {return flats;}
	protected List<Object> jsonDataList; public List<Object> getJsonDataList() {return jsonDataList;} public void setJsonDataList(List<Object> jsonDataList) {this.jsonDataList = jsonDataList;}
	protected String jsonStream; public String getJsonStream() {return jsonStream;}
	
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	private Comparator<COLUMN> comparatorColumn;
	private Comparator<ROW> comparatorRow;
	private Comparator<CELL> comparatorCell;

	public AbstractJeeslReport(String localeCode, final Class<L> cL,final Class<D> cD, final Class<CATEGORY> cCategory, final Class<REPORT> cReport)
	{
		this.cL=cL;
		this.cD=cD;
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.localeCode=localeCode;
		debugOnInfo = false;
		developmentMode = false;
		
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportGroupComparator.Type.position);
		comparatorColumn = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportColumnComparator.Type.position);
		comparatorRow = new IoReportRowComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportRowComparator.Type.position);
		comparatorCell = new IoReportCellComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportCellComparator.Type.position);
		
		mapGroupVisibilityToggle = new HashMap<GROUP,Boolean>();
		showHeaderGroup = true;
		
		jobCodeProcessor = new JobCodeProcessor();
		
		buildHeaders();
	}
	
	protected void initIo(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, Class<?> classReport, final Class<IMPLEMENTATION> cImplementation, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CELL> cCell, final Class<STYLE> cStyle, final Class<CDT> cDataType, final Class<CW> cColumnWidth, Class<RT> cRowType, final Class<TRANSFORMATION> cTransformation)
	{
		if(fReport!=null)
		{
			ffReport = ReportFactoryFactory.factory(cL,cD,cCategory,cReport,cImplementation,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cStyle,cDataType,cColumnWidth,cRowType);
			efLang = EjbLangFactory.factory(cL);
			efColumn = ffReport.column();
			
			if(reportSettingTransformation==null)
			{
				try {reportSettingTransformation = fReport.fByCode(cTransformation, JeeslReportSetting.Transformation.none);}
				catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
			}
			
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
						
						// Only set the toggled visibilities to true on the first time
						if(mapGroupVisibilityToggle.isEmpty()){for(GROUP g : ioSheet.getGroups()){mapGroupVisibilityToggle.put(g,true);}}
						
						calculateSheetSettings();
						xlsFactory = new XlsFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION>(localeCode,cL,cD,cCategory,cReport,cImplementation,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cStyle,cDataType,cColumnWidth,cRowType,ioWorkbook);
						
						//Sorting of the report structure (and lazyloading of Template)
						for(SHEET s : ioWorkbook.getSheets())
						{
							Collections.sort(s.getGroups(), comparatorGroup);
							Collections.sort(s.getRows(), comparatorRow);
							for(GROUP g : s.getGroups()) {Collections.sort(g.getColumns(), comparatorColumn);}
							for(ROW r : s.getRows())
							{
								if(r.getTemplate()!=null)
								{
									r.setTemplate(fReport.load(r.getTemplate()));
									Collections.sort(r.getTemplate().getCells(),comparatorCell);
								}
							}
						}
					}
				}
			}
			catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
			
			if(debugOnInfo)
			{
				logger.info(StringUtil.stars());
				logger.info("Debugging: "+classReport.getSimpleName());
				logger.info("ShowHeaderGroup: "+showHeaderGroup);
				logger.info("showHeaderColumn: "+showHeaderColumn);
				if(groups!=null)
				{
					for(GROUP g : groups)
					{
						logger.info("\t"+g.getPosition()+". "+g.getClass().getSimpleName()+" (childs:"+mapGroupChilds.get(g)+"): "+g.getName().get(localeCode).getLang());
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
		}
		else{logger.warn("Trying to super.initIo(), but "+JeeslIoReportFacade.class.getSimpleName()+" is null");}
	}
	
	protected <A extends UtilsStatus<A,L,D>> void applyTransformation(JeeslReportSetting.Transformation transformation, List<EjbWithId> last, String[] localeCodes, JeeslReportAggregationLevelFactory pf)
	{
		if(transformation.equals(JeeslReportSetting.Transformation.last))
		{
			for(GROUP group : groups)
			{
				if(group.getColumns().size()==1 && !group.getColumns().get(0).getQueryCell().startsWith("g"))
				{
					COLUMN original = group.getColumns().get(0);
					group.getColumns().clear();
					group.setQueryColumns(original.getQueryCell().replaceAll("d","list"));
					int arrayIndex = 0;
					for(EjbWithId ejb : last)
					{
						COLUMN c = efColumn.build(group);
						c.setId(group.getColumns().size()+1);
						c.setPosition(group.getColumns().size()+1);
						c.setColumWidth(original.getColumWidth());
						c.setDataType(original.getDataType());
						c.setVisible(true);
						c.setShowLabel(true);
						c.setPosition(arrayIndex);
						c.setName(efLang.createEmpty(localeCodes));
						for(String localeCode : localeCodes){c.getName().get(localeCode).setLang(pf.buildTreeLevelName(localeCode, ejb));}
						c.setQueryCell(original.getQueryCell());
						
						group.getColumns().add(c);
						arrayIndex++;
					}
				}
			}
		}
		calculateSheetSettings();
	}
	
	protected void calculateSheetSettings()
	{
		groupsAll = EjbIoReportColumnGroupFactory.toListVisibleGroups(ioSheet);
		groups = EjbIoReportColumnGroupFactory.toListVisibleGroups(ioSheet,mapGroupVisibilityToggle);
		columns = EjbIoReportColumnFactory.toListVisibleColumns(ioSheet,mapGroupVisibilityToggle);
		
		mapGroupChilds = EjbIoReportColumnGroupFactory.toMapVisibleGroupSize(ioSheet);
		mapGroupColumns = EjbIoReportColumnGroupFactory.toMapVisibleGroupColumns(ioSheet);

		Collections.sort(groupsAll, comparatorGroup);
		Collections.sort(groups, comparatorGroup);
		Collections.sort(columns, comparatorColumn);
		
		showHeaderGroup=false;
		showHeaderColumn=false;
		for(GROUP g : groups)
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
	
	public void toggleGroupVisibility(GROUP g)
	{
		if(mapGroupVisibilityToggle.containsKey(g))
		{
			mapGroupVisibilityToggle.put(g, !mapGroupVisibilityToggle.get(g));
		}
		else
		{
			mapGroupVisibilityToggle.put(g,true);
		}
	}
	
	private void buildHeaders()
	{
		headers = new ArrayList<String>();
	}
	
	public void buildJson()
	{
		jsonStream = "";
		try {jsonStream = JsonUtil.toString(jsonDataList);}
		catch (JsonProcessingException e) {e.printStackTrace();}
	}
}