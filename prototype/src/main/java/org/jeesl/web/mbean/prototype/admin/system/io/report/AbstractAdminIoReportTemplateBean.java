package org.jeesl.web.mbean.prototype.admin.system.io.report;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.factory.ejb.system.io.report.EjbIoReportCellFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportTemplateFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportCellComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportTemplateComparator;
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
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoReportTemplateBean <L extends UtilsLang,D extends UtilsDescription,
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
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
										RC extends UtilsStatus<RC,L,D>,
										RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RST extends UtilsStatus<RST,L,D>,
										RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>
										>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoReportTemplateBean.class);
	
	protected JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport;
	
	private Class<TEMPLATE> cTemplate;
	private Class<CELL> cCell;
	
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	private List<CELL> cells; public List<CELL> getCells() {return cells;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}
	private CELL cell; public CELL getCell() {return cell;} public void setCell(CELL cell) {this.cell = cell;}

	private Comparator<TEMPLATE> comparatorTemplate;
	private Comparator<CELL> comparatorCell;
	
	private EjbIoReportTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efTemplate;
	private EjbIoReportCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> efCell;
		
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> fReport, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<REPORT> cReport, Class<IMPLEMENTATION> cImplementation, Class<WORKBOOK> cWorkbook, Class<SHEET> cSheet, Class<GROUP> cGroup, Class<COLUMN> cColumn, Class<ROW> cRow, Class<TEMPLATE> cTemplate, Class<CELL> cCell, Class<CDT> cDataType, Class<CW> cColumnWidth, Class<RT> cRowType, Class<RC> cRevisionCategory)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fReport=fReport; 
		this.cTemplate = cTemplate;
		this.cCell = cCell;
		
		ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION> ff = ReportFactoryFactory.factory(cLang,cDescription,cReport,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cDataType,cColumnWidth);
		efTemplate = ff.template();
		efCell = ff.cell();
				
		comparatorTemplate = new IoReportTemplateComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportTemplateComparator.Type.position);
		comparatorCell = new IoReportCellComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,CDT,CW,RT,ENTITY,ATTRIBUTE>().factory(IoReportCellComparator.Type.position);

		reloadTemplates();
	}
	
	private void reset(boolean rTemplate, boolean rCell)
	{
		if(rTemplate){template=null;}
		if(rCell){cell=null;}
	}
	
	//*************************************************************************************
	private void reloadTemplates()
	{
		templates = fReport.all(cTemplate);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cTemplate,templates));}
//		Collections.sort(templates,comparatorTemplate);
	}
	
	public void addTemplate()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		template = efTemplate.build();
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));
		reset(false,true);
	}
	
	private void reloadTemplate()
	{
		template = fReport.load(template);
		cells = template.getCells();
		
		Collections.sort(cells, comparatorCell);
	}
	
	public void selectTemplate() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(template));}
		template = fReport.find(cTemplate, template);
		template = efLang.persistMissingLangs(fReport,langs,template);
		template = efDescription.persistMissingLangs(fReport,langs,template);
		
		reloadTemplate();
		reset(false,true);
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(template));}
		template = fReport.save(template);
		reloadTemplates();
		reloadTemplate();
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
	
	public void cancelTemplate() {reset(true,true);}
	
	//*************************************************************************************

	public void addCell()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cCell));}
		cell = efCell.build(template);
		cell.setName(efLang.createEmpty(langs));
		cell.setDescription(efDescription.createEmpty(langs));
		reset(false,false);
	}
	
	public void selectCell()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(cell));}
		cell = fReport.find(cCell, cell);
		reset(false,false);
	}
		
	public void saveCell() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(cell));}
		try
		{
			cell = fReport.save(cell);
			reloadTemplate();
			
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}

	public void cancelCell() {reset(false,true);}
    
	//*************************************************************************************
	protected void reorderTemplates() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport,templates);Collections.sort(templates,comparatorTemplate);}
	
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