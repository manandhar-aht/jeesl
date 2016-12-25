package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.jeesl.controller.handler.ui.helper.UiHelperIoReport;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportSheetComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoReportBean <L extends UtilsLang,D extends UtilsDescription,
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
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
										RC extends UtilsStatus<RC,L,D>,
										RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RST extends UtilsStatus<RST,L,D>,
										RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoReportBean.class);
	
	protected JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport;
	
	private Class<CATEGORY> cCategory;
	private Class<REPORT> cReport;
	private Class<IMPLEMENTATION> cImplementation;
//	private Class<WORKBOOK> cWorkbook;
	private Class<SHEET> cSheet;
	private Class<GROUP> cGroup;
	private Class<COLUMN> cColumn;
	private Class<RC> cRevisionCategory;
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<RC> revisionCategories; public List<RC> getRevisionCategories() {return revisionCategories;}
	private List<REPORT> reports; public List<REPORT> getReports() {return reports;}
	private List<IMPLEMENTATION> implementations; public List<IMPLEMENTATION> getImplementations() {return implementations;}
	private List<SHEET> sheets; public List<SHEET> getSheets() {return sheets;}
	private List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	private List<COLUMN> columns; public List<COLUMN> getColumns() {return columns;}
	
	private RC revisionCategory; public RC getRevisionCategory() {return revisionCategory;} public void setRevisionCategory(RC revisionCategory) {this.revisionCategory = revisionCategory;}
	private REPORT report; public REPORT getReport() {return report;} public void setReport(REPORT report) {this.report = report;}
	private SHEET sheet; public SHEET getSheet() {return sheet;} public void setSheet(SHEET sheet) {this.sheet = sheet;}
	private GROUP group; public GROUP getGroup() {return group;}public void setGroup(GROUP group) {this.group = group;}
	private COLUMN column; public COLUMN getColumn() {return column;} public void setColumn(COLUMN column) {this.column = column;}
	
	private UiHelperIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> uiHelper; public UiHelperIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> getUiHelper() {return uiHelper;}
	private SbMultiStatusHandler<L,D,CATEGORY> sbhCategory; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory() {return sbhCategory;}
	
	private Comparator<REPORT> comparatorReport;
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	private Comparator<COLUMN> comparatorColumn;
	
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efReport;
	private EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efWorkbook;
	private EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efSheet;
	private EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efGroup;
	private EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efColumn;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<REPORT> cReport, Class<IMPLEMENTATION> cImplementation, Class<WORKBOOK> cWorkbook, Class<SHEET> cSheet, Class<GROUP> cGroup, Class<COLUMN> cColumn, Class<RC> cRevisionCategory)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fReport=fReport;
		
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cImplementation=cImplementation;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
		this.cColumn=cColumn;
		this.cRevisionCategory=cRevisionCategory;

		ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> ef = ReportFactoryFactory.factory(cLang,cDescription,cReport,cWorkbook,cSheet,cGroup,cColumn);
		efReport = ef.report();
		efWorkbook = ef.workbook();
		efSheet = ef.sheet();
		efGroup = ef.group();
		efColumn = ef.column();
		
		uiHelper = new UiHelperIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>();
		categories = fReport.allOrderedPositionVisible(cCategory);
		revisionCategories = fReport.allOrderedPositionVisible(cRevisionCategory);
		
		comparatorReport = new IoReportComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportComparator.Type.position);
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup = new IoReportGroupComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportGroupComparator.Type.position);
		comparatorColumn = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>().factory(IoReportColumnComparator.Type.position);
		
		implementations = fReport.allOrderedPositionVisible(cImplementation);
		
		sbhCategory = new SbMultiStatusHandler<L,D,CATEGORY>(cCategory,categories);
//		sbhCategory.selectAll();
		reloadReports();
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName());
		sbhCategory.multiToggle(o);
		reloadReports();
		cancelReport();
	}
	
	private void reset(boolean rReport, boolean rSheet, boolean rGroup, boolean rColumn)
	{
		if(rReport){report=null;}
		if(rSheet){sheet=null;}
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
		reset(false,true,true,true);
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
		reset(false,true,true,true);
		uiHelper.check(report);
	}
	
	public void saveReport() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(report));}
		if(report.getCategory()!=null){report.setCategory(fReport.find(cCategory, report.getCategory()));}
		if(report.getImplementation()!=null){report.setImplementation(fReport.find(cImplementation, report.getImplementation()));}
		report = fReport.save(report);
		reloadReports();
		reloadReport();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
/*	
	public void rmTemplate() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(template));}
		fTemplate.rm(template);
		template=null;
		bMessage.growlSuccessRemoved();
		reloadTemplates();
		updatePerformed();
	}
*/	
	public void cancelReport()
	{
		reset(true,true,true,true);
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
		reset(false,false,true,true);
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
		reset(false,false,true,true);
		uiHelper.check(sheet);
		uiHelper.check(group);
	}
	
	private void reloadSheet()
	{
		sheet = fReport.load(sheet,false);
		groups = sheet.getGroups();
		Collections.sort(groups, comparatorGroup);
	}
		
	public void saveSheet() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(sheet));}
		try
		{
			sheet = fReport.save(sheet);
			reloadReport();
			reloadSheet();
			bMessage.growlSuccessSaved();
			updatePerformed();
			uiHelper.check(sheet);
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	/*	
	public void rmToken() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(token));}
		fTemplate.rm(token);
		token=null;
		bMessage.growlSuccessRemoved();
		reloadTemplate();
		updatePerformed();
	}
*/	
	public void cancelSheet()
	{
		reset(false,true,true,true);
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
		reset(false,false,false,true);
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
		reset(false,false,false,true);
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
		reset(false,false,true,true);
		uiHelper.check(group);
	}
	
	public void cancelGroup()
	{
		reset(false,false,true,true);
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
		reset(false,false,false,true);
	}
	
	public void changeRevisionCategory()
	{
		revisionCategory = fReport.find(cRevisionCategory, revisionCategory);
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(revisionCategory));}
	}
    
	//*************************************************************************************
	protected void reorderReports() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cReport, reports);Collections.sort(reports, comparatorReport);}
	protected void reorderSheets() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cSheet, sheets);Collections.sort(sheets, comparatorSheet);}
	protected void reorderGroups() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cGroup, groups);Collections.sort(groups, comparatorGroup);}
	protected void reorderColumns() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cColumn, columns);Collections.sort(columns, comparatorColumn);}
	
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