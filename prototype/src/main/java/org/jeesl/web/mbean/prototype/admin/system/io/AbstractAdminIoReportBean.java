package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.controller.handler.ui.helper.UiHelperIoReport;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactoryFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
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
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoReportBean <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoReportBean.class);
	
	protected JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport;
	
	private Class<CATEGORY> cCategory;
	private Class<REPORT> cReport;
//	private Class<WORKBOOK> cWorkbook;
	private Class<SHEET> cSheet;
	private Class<GROUP> cGroup;
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<REPORT> reports; public List<REPORT> getReports() {return reports;}
	private List<SHEET> sheets; public List<SHEET> getSheets() {return sheets;}
	private List<GROUP> groups; public List<GROUP> getGroups() {return groups;}
	
	private REPORT report; public REPORT getReport() {return report;} public void setReport(REPORT report) {this.report = report;}
	private SHEET sheet; public SHEET getSheet() {return sheet;} public void setSheet(SHEET sheet) {this.sheet = sheet;}
	private GROUP group; public GROUP getGroup() {return group;}public void setGroup(GROUP group) {this.group = group;}
	
	private UiHelperIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> uiHelper; public UiHelperIoReport<L, D, CATEGORY, REPORT, WORKBOOK, SHEET, GROUP, COLUMN, FILLING, TRANSFORMATION> getUiHelper() {return uiHelper;}
	private SbMultiStatusHandler<L,D,CATEGORY> sbhCategory; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory() {return sbhCategory;}
	
	private Comparator<REPORT> comparatorReport;
	private Comparator<SHEET> comparatorSheet;
	private Comparator<GROUP> comparatorGroup;
	
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> efReport;
	private EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> efWorkbook;
	private EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> efSheet;
	private EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> efGroup;
	private EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> efColumn;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<REPORT> cReport, Class<WORKBOOK> cWorkbook, Class<SHEET> cSheet, Class<GROUP> cGroup, Class<COLUMN> cColumn)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fReport=fReport;
		
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cSheet=cSheet;
		this.cGroup=cGroup;

		EjbIoReportFactoryFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> ef = EjbIoReportFactoryFactory.factory(cLang,cDescription,cReport,cWorkbook,cSheet,cGroup,cColumn);
		efReport = ef.report();
		efWorkbook = ef.workbook();
		efSheet = ef.sheet();
		efGroup = ef.group();
		efColumn = ef.column();
		
		uiHelper = new UiHelperIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>();
		categories = fReport.allOrderedPositionVisible(cCategory);
		
		comparatorReport = new IoReportComparator<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>().factory(IoReportComparator.Type.position);
		comparatorSheet = new IoReportSheetComparator<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>().factory(IoReportSheetComparator.Type.position);
		comparatorGroup = new IoReportGroupComparator<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>().factory(IoReportGroupComparator.Type.position);
		
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
		report = fReport.load(report);
		sheets = report.getWorkbook().getSheets();
		
		Collections.sort(sheets, comparatorSheet);
	}
	
	public void selectReport()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(report));}
		report = fReport.find(cReport, report);
		report = efLang.persistMissingLangs(fReport,langs,report);
		report = efDescription.persistMissingLangs(fReport,langs,report);
		reloadReport();
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
		report = null;
		sheet=null;
		uiHelper.check(report);
		uiHelper.check(sheet);
	}
		
	//*************************************************************************************

	public void addSheet()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cSheet));}
		sheet = efSheet.build(report.getWorkbook());
		sheet.setName(efLang.createEmpty(langs));
		sheet.setDescription(efDescription.createEmpty(langs));
		uiHelper.check(sheet);
	}
	
	public void selectSheet()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(sheet));}
		sheet = fReport.find(cSheet, sheet);
		uiHelper.check(sheet);
	}
		
	public void saveSheet() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(sheet));}
		try
		{
			sheet = fReport.save(sheet);
			reloadReport();
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
		sheet=null;
		uiHelper.check(sheet);
	}
	
	//*************************************************************************************

	public void addGroup()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cGroup));}
/*		sheet = efSheet.build(report.getWorkbook());
		sheet.setName(efLang.createEmpty(langs));
		sheet.setDescription(efDescription.createEmpty(langs));
		uiHelper.check(sheet);
*/	}
	
	public void selectGroup()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(group));}
		group = fReport.find(cGroup, group);
//		uiHelper.check(sheet);
	}
    
	//*************************************************************************************
	protected void reorderReports() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cReport, reports);Collections.sort(reports, comparatorReport);}
	protected void reorderSheets() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cSheet, sheets);Collections.sort(sheets, comparatorSheet);}
	protected void reorderGroups() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cGroup, groups);Collections.sort(groups, comparatorGroup);}
	
	protected void updatePerformed(){}	
	
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}