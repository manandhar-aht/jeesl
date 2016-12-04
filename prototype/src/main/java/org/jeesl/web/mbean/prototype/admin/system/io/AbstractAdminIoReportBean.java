package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactoryFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.util.comparator.ejb.system.io.IoReportComparator;
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
//	private Class<SHEET> cToken;
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<REPORT> reports; public List<REPORT> getReports() {return reports;}
	
	private REPORT report; public REPORT getReport() {return report;} public void setReport(REPORT report) {this.report = report;}
	
	private SbMultiStatusHandler<L,D,CATEGORY> sbhCategory; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory() {return sbhCategory;}
	private Comparator<REPORT> comparatorReport;
	
	private EjbIoReportFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> efReport;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> fReport, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<REPORT> cReport, Class<WORKBOOK> cWorkbook, Class<SHEET> cSheet)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fReport=fReport;
		
		this.cCategory=cCategory;
		this.cReport=cReport;

		EjbIoReportFactoryFactory<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION> ef = EjbIoReportFactoryFactory.factory(cLang, cDescription, cReport, cWorkbook, cSheet);
		efReport = ef.report();
		
		categories = fReport.allOrderedPositionVisible(cCategory);
		
		comparatorReport = new IoReportComparator<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>().factory(IoReportComparator.Type.position);
		
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
	}
	
	private void reloadReport()
	{
		report = fReport.load(report);
		/*		tokens = template.getTokens();
		definitions = template.getDefinitions();
		
		Collections.sort(tokens, comparatorToken);
		Collections.sort(definitions, comparatorDefinition);
*/	}
	
	public void selectReport()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(report));}
		report = fReport.find(cReport, report);
		report = efLang.persistMissingLangs(fReport,langs,report);
		report = efDescription.persistMissingLangs(fReport,langs,report);
		reloadReport();
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
	}
		
	//*************************************************************************************
/*
	public void addToken() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cToken));}
		token = efToken.build(template);
		token.setName(efLang.createEmpty(langs));
		token.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectToken() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(token));}
		token = fTemplate.find(cToken, token);
	}
	
	public void saveToken() throws UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(token));}
		try
		{
			token = fTemplate.save(token);
			reloadTemplate();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
	public void rmToken() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(token));}
		fTemplate.rm(token);
		token=null;
		bMessage.growlSuccessRemoved();
		reloadTemplate();
		updatePerformed();
	}
	
	public void cancelToken()
	{
		token=null;
	}
*/	
	//*************************************************************************************
    
	//*************************************************************************************
	protected void reorderReports() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cReport, reports);Collections.sort(reports, comparatorReport);}
//	protected void reorderTokens() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport, cToken, tokens);Collections.sort(tokens, comparatorToken);}
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