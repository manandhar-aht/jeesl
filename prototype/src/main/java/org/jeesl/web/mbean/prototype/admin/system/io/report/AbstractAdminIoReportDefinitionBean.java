package org.jeesl.web.mbean.prototype.admin.system.io.report;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.controller.handler.ui.helper.UiHelperIoReport;
import org.jeesl.factory.builder.system.ReportFactoryProvider;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportRowComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportSheetComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoReportDefinitionBean <L extends UtilsLang,D extends UtilsDescription,
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
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
										RC extends UtilsStatus<RC,L,D>,
										RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RST extends UtilsStatus<RST,L,D>,
										RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>
										>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoReportDefinitionBean.class);
	
	protected JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport;
	
	private Class<CATEGORY> cCategory;
	private Class<REPORT> cReport;
	private Class<IMPLEMENTATION> cImplementation;
//	private Class<WORKBOOK> cWorkbook;
	private Class<SHEET> cSheet;
	private Class<GROUP> cGroup;
	private Class<COLUMN> cColumn;
	private Class<ROW> cRow;
	private Class<TEMPLATE> cTemplate;
	private Class<STYLE> cStyle;
	
	private final Class<TLS> cTls;
	
	private Class<CDT> cDataType;
	private Class<CW> cColumnWidth;
	private Class<RT> cRowType;
	private Class<RC> cRevisionCategory;
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<RC> revisionCategories; public List<RC> getRevisionCategories() {return revisionCategories;}
	private List<CW> columnWidths; public List<CW> getColumnWidths() {return columnWidths;}
	private List<RT> rowTypes; public List<RT> getRowTypes() {return rowTypes;}
	private List<CDT> attributeTypes; public List<CDT> getAttributeTypes() {return attributeTypes;}
	private List<REPORT> reports; public List<REPORT> getReports() {return reports;}
	private List<IMPLEMENTATION> implementations; public List<IMPLEMENTATION> getImplementations() {return implementations;}
	private List<SHEET> sheets; public List<SHEET> getSheets() {return sheets;}
	private List<ROW> rows; public List<ROW> getRows() {return rows;}
	private List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	private List<COLUMN> columns; public List<COLUMN> getColumns() {return columns;}
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	private List<STYLE> styles; public List<STYLE> getStyles() {return styles;}
	private List<TLS> trafficLightScopes; public List<TLS> getTrafficLightScopes() {return trafficLightScopes;}
	
	private RC revisionCategory; public RC getRevisionCategory() {return revisionCategory;} public void setRevisionCategory(RC revisionCategory) {this.revisionCategory = revisionCategory;}
	private REPORT report; public REPORT getReport() {return report;} public void setReport(REPORT report) {this.report = report;}
	private SHEET sheet; public SHEET getSheet() {return sheet;} public void setSheet(SHEET sheet) {this.sheet = sheet;}
	private ROW row; public ROW getRow() {return row;} public void setRow(ROW row) {this.row = row;}
	private GROUP group; public GROUP getGroup() {return group;}public void setGroup(GROUP group) {this.group = group;}
	private COLUMN column; public COLUMN getColumn() {return column;} public void setColumn(COLUMN column) {this.column = column;}
	
	private UiHelperIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> uiHelper; public UiHelperIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> getUiHelper() {return uiHelper;}
	private SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	
	private Comparator<REPORT> comparatorReport;
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	private Comparator<COLUMN> comparatorColumn;
	private Comparator<ROW> comparatorRow;
	
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efReport;
	private EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efWorkbook;
	private EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efSheet;
	private EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efGroup;
	private EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efColumn;
	private EjbIoReportRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efRow;
	
	protected AbstractAdminIoReportDefinitionBean(final Class<TLS> cTls)
	{
		this.cTls = cTls;
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<REPORT> cReport, Class<IMPLEMENTATION> cImplementation, Class<WORKBOOK> cWorkbook, Class<SHEET> cSheet, Class<GROUP> cGroup, Class<COLUMN> cColumn, Class<ROW> cRow, Class<TEMPLATE> cTemplate, Class<CELL> cCell, Class<STYLE> cStyle, Class<CDT> cDataType, Class<CW> cColumnWidth, Class<RT> cRowType, Class<RC> cRevisionCategory)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fReport=fReport;
		
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cImplementation=cImplementation;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
		this.cColumn=cColumn;
		this.cRow=cRow;
		this.cTemplate=cTemplate;
		this.cStyle=cStyle;
		this.cDataType=cDataType;
		this.cColumnWidth=cColumnWidth;
		this.cRowType=cRowType;
		this.cRevisionCategory=cRevisionCategory;


		ReportFactoryProvider<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> ef = ReportFactoryProvider.factory(cLang,cDescription,cCategory,cReport,cImplementation,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cStyle,cDataType,cColumnWidth,cRowType);
		efReport = ef.report();
		efWorkbook = ef.workbook();
		efSheet = ef.sheet();
		efGroup = ef.group();
		efColumn = ef.column();
		efRow = ef.row();
		
		uiHelper = new UiHelperIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION>();
		categories = fReport.allOrderedPositionVisible(cCategory);
		revisionCategories = fReport.allOrderedPositionVisible(cRevisionCategory);
		
		comparatorReport = new IoReportComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportComparator.Type.position);
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportGroupComparator.Type.position);
		comparatorColumn = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportColumnComparator.Type.position);
		comparatorRow  = new IoReportRowComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportRowComparator.Type.position);
		
		implementations = fReport.allOrderedPositionVisible(cImplementation);
		attributeTypes = fReport.allOrderedPositionVisible(cDataType);
		columnWidths = fReport.allOrderedPositionVisible(cColumnWidth);
		rowTypes = fReport.allOrderedPositionVisible(cRowType);
		templates = fReport.allOrderedPositionVisible(cTemplate);
		styles = fReport.allOrderedPositionVisible(cStyle);
		trafficLightScopes = fReport.allOrderedPositionVisible(cTls);
		
		sbhCategory = new SbMultiHandler<CATEGORY>(cCategory,categories,this);
//		sbhCategory.selectAll();
		reloadReports();
	}
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		reloadReports();
		cancelReport();
	}
	
	
	private void reset(boolean rReport, boolean rSheet, boolean rRow, boolean rGroup, boolean rColumn)
	{
		if(rReport){report=null;}
		if(rSheet){sheet=null;}
		if(rRow){row=null;}
		if(rGroup){group=null;}
		if(rColumn){column=null;}
	}
	
	//*************************************************************************************
	private void reloadReports()
	{
		reports = fReport.fReports(sbhCategory.getSelected(), true);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cReport,reports));}
		Collections.sort(reports,comparatorReport);
	}
	
	public void addReport()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cReport));}
		report = efReport.build(null);
		report.setName(efLang.createEmpty(langs));
		report.setDescription(efDescription.createEmpty(langs));
		report.setWorkbook(efWorkbook.build(report));
		uiHelper.check(report);
		reset(false,true,true,true,true);
	}
	
	private void reloadReport()
	{
		if(report.getWorkbook()==null)
		{
			try
			{
				WORKBOOK wb = efWorkbook.build(report);
				fReport.saveTransaction(wb);
			}
			catch (UtilsConstraintViolationException e) {logger.error(e.getMessage());}
			catch (UtilsLockingException e) {logger.error(e.getMessage());}
		}
		report = fReport.load(report,false);
		sheets = report.getWorkbook().getSheets();
		
		Collections.sort(sheets, comparatorSheet);
	}
	
	public void selectReport() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(report));}
		report = fReport.find(cReport, report);
		report = efLang.persistMissingLangs(fReport,langs,report);
		report = efDescription.persistMissingLangs(fReport,langs,report);
		if(report.getWorkbook()==null)
		{
			report.setWorkbook(efWorkbook.build(report));
			report = fReport.saveTransaction(report);
		}
		reloadReport();
		reset(false,true,true,true,true);
		uiHelper.check(report);
	}
	
	public void saveReport() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(report));}
		if(report.getCategory()!=null){report.setCategory(fReport.find(cCategory, report.getCategory()));}
		report = fReport.save(report);
		reloadReports();
		reloadReport();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmReport() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(report));}
		fReport.rm(report);
		reset(true,true,true,true,true);
		bMessage.growlSuccessRemoved();
		reloadReports();
		updatePerformed();
	}
	
	public void cancelReport()
	{
		reset(true,true,true,true,true);
		uiHelper.check(report);
		uiHelper.check(sheet);
		uiHelper.check(group);
	}
		
	//*************************************************************************************

	public void addSheet()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cSheet));}
		sheet = efSheet.build(report.getWorkbook());
		sheet.setName(efLang.createEmpty(langs));
		sheet.setDescription(efDescription.createEmpty(langs));
		uiHelper.check(sheet);
		reset(false,false,true,true,true);
	}
	
	public void selectSheet()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(sheet));}
		sheet = fReport.find(cSheet, sheet);
		if(sheet.getCode()==null)
		{
			try
			{
				sheet.setCode(UUID.randomUUID().toString());
				sheet = fReport.save(sheet);
			}
			catch (UtilsConstraintViolationException e) {logger.error(e.getMessage());}
			catch (UtilsLockingException e) {logger.error(e.getMessage());}
		}
		reloadSheet();
		reset(false,false,true,true,true);
		uiHelper.check(sheet);
		uiHelper.check(group);
	}
	
	private void reloadSheet()
	{
		sheet = fReport.load(sheet,false);
		groups = sheet.getGroups();
		rows = sheet.getRows();
		Collections.sort(groups, comparatorGroup);
		Collections.sort(rows,comparatorRow);
	}
		
	public void saveSheet() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(sheet));}
		try
		{
			if(sheet.getImplementation()!=null){sheet.setImplementation(fReport.find(cImplementation, sheet.getImplementation()));}
			sheet = fReport.save(sheet);
			reloadReport();
			reloadSheet();
			bMessage.growlSuccessSaved();
			updatePerformed();
			uiHelper.check(sheet);
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
		
	public void rmSheet() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(sheet));}
		fReport.rmSheet(sheet);
		reset(false,true,true,true,true);
		bMessage.growlSuccessRemoved();
		reloadReport();
		updatePerformed();
	}
	
	public void cancelSheet()
	{
		reset(false,true,true,true,true);
		uiHelper.check(sheet);
		uiHelper.check(group);
	}
	
	//*************************************************************************************

	public void addGroup()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cGroup));}
		group = efGroup.build(sheet);
		group.setName(efLang.createEmpty(langs));
		group.setDescription(efDescription.createEmpty(langs));
		reset(false,false,true,false,true);
		uiHelper.check(group);
	}
	
	public void selectGroup()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(group));}
		group = fReport.find(cGroup, group);
		if(group.getCode()==null)
		{
			try
			{
				group.setCode(UUID.randomUUID().toString());
				group = fReport.save(group);
			}
			catch (UtilsConstraintViolationException e) {logger.error(e.getMessage());}
			catch (UtilsLockingException e) {logger.error(e.getMessage());}
		}
		reloadGroup();
		reset(false,false,true,false,true);
		uiHelper.check(group);
	}
	
	private void reloadGroup()
	{
		group = fReport.load(group);
		columns = group.getColumns();
		Collections.sort(columns, comparatorColumn);
	}
	
	public void saveGroup() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(group));}
		try
		{
			if(group.getStyleHeader()!=null){group.setStyleHeader(fReport.find(cStyle,group.getStyleHeader()));}
			group = fReport.save(group);
			reloadReport();
			reloadSheet();
			reloadGroup();
			bMessage.growlSuccessSaved();
			updatePerformed();
			uiHelper.check(group);
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
	public void rmGroup() throws UtilsConstraintViolationException
	{
		fReport.rmGroup(group);
		reloadReport();
		reloadSheet();
		reset(false,false,true,true,true);
		uiHelper.check(group);
	}
	
	public void cancelGroup()
	{
		reset(false,false,true,true,true);
		uiHelper.check(group);
	}
	
	//*************************************************************************************

	public void addColumn()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cColumn));}
		column = efColumn.build(group);
		column.setName(efLang.createEmpty(langs));
		column.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectColumn()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(column));}
		if(column.getCode()==null)
		{
			try
			{
				column.setCode(UUID.randomUUID().toString());
				column = fReport.save(column);
			}
			catch (UtilsConstraintViolationException e) {logger.error(e.getMessage());}
			catch (UtilsLockingException e) {logger.error(e.getMessage());}
		}
		reloadColumn();
	}
	
	private void reloadColumn()
	{
		column = fReport.find(cColumn, column);
	}
	
	public void saveColumn() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(column));}
		try
		{
			if(column.getDataType()!=null) {column.setDataType(fReport.find(cDataType,column.getDataType()));}
			if(column.getColumWidth()!=null) {column.setColumWidth(fReport.find(cColumnWidth,column.getColumWidth()));}
			if(column.getTrafficLightScope()!=null) {column.setTrafficLightScope(fReport.find(cTls,column.getTrafficLightScope()));}
			column.setGroup(fReport.find(cGroup,column.getGroup()));
			column = fReport.save(column);
			reloadReport();
			reloadSheet();
			reloadGroup();
			reloadColumn();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
		
	public void rmColumn() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(column));}
		fReport.rmColumn(column);
		column=null;
		bMessage.growlSuccessRemoved();
		reloadGroup();
		updatePerformed();
	}
	
	public void cancelColumn()
	{
		reset(false,false,false,false,true);
	}
	
	public void changeColumnDataType()
	{
		if(column.getDataType()!=null){column.setDataType(fReport.find(cDataType,column.getDataType()));}
	}
	
	public void changeRevisionCategory()
	{
		revisionCategory = fReport.find(cRevisionCategory, revisionCategory);
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(revisionCategory));}
	}
	
	//*************************************************************************************
	
	public void addRow()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cRow));}
		row = efRow.build(sheet);
		row.setName(efLang.createEmpty(langs));
		row.setDescription(efDescription.createEmpty(langs));
		reset(false,false,false,true,true);
	}
	
	public void selectRow()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(row));}
		reloadRow();
	}
	
	private void reloadRow()
	{
		row = fReport.find(cRow, row);
	}
	
	public void saveRow() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(row));}
		try
		{
			if(row.getDataType()!=null){row.setDataType(fReport.find(cDataType,row.getDataType()));}
			if(row.getTemplate()!=null){row.setTemplate(fReport.find(cTemplate, row.getTemplate()));}
			row.setType(fReport.find(cRowType,row.getType()));
			row = fReport.save(row);
			reloadReport();
			reloadSheet();
			reloadRow();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
	public void rmRow() throws UtilsConstraintViolationException
	{
		fReport.rmRow(row);
		reloadReport();
		reloadSheet();
		cancelRow();
	}
	public void cancelRow(){reset(false,false,true,true,true);}
    
	//*************************************************************************************
	protected void reorderReports() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cReport, reports);Collections.sort(reports, comparatorReport);}
	protected void reorderSheets() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cSheet, sheets);Collections.sort(sheets, comparatorSheet);}
	protected void reorderGroups() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cGroup, groups);Collections.sort(groups, comparatorGroup);}
	protected void reorderColumns() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cColumn, columns);Collections.sort(columns, comparatorColumn);}
	protected void reorderRows() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cRow, rows);Collections.sort(rows, comparatorRow);}
	
	protected void updatePerformed(){}	
	
	@SuppressWarnings("rawtypes")
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}