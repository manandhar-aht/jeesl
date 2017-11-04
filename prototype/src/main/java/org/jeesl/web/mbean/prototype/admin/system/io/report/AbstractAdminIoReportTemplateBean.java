package org.jeesl.web.mbean.prototype.admin.system.io.report;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.factory.builder.system.ReportFactoryBuilder;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportCellFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportTemplateFactory;
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
										REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
										IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
										WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
										SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
										GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
										COLUMN extends JeeslReportColumn<L,D,GROUP,STYLE,CDT,CW,TLS>,
										ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
										TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
										CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
										STYLE extends JeeslReportStyle<L,D>,
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
										RE extends JeeslRevisionEntity<L,D,RC,?,?,?,?,RE,?,RA,CDT>,
										RA extends JeeslRevisionAttribute<L,D,RC,?,?,?,?,RE,?,RA,CDT>
										>
	extends AbstractIoReportBean<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION,RC,RE,RA>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoReportTemplateBean.class);
	
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	private List<CELL> cells; public List<CELL> getCells() {return cells;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}
	private CELL cell; public CELL getCell() {return cell;} public void setCell(CELL cell) {this.cell = cell;}

	private Comparator<TEMPLATE> comparatorTemplate;
	private Comparator<CELL> comparatorCell;
	
	private EjbIoReportTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efTemplate;
	private EjbIoReportCellFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efCell;
		
	public AbstractAdminIoReportTemplateBean(final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RC,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport)
	{
		super(fbReport);
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport)
	{
		super.initSuperReport(langs,bMessage,fReport);
		
		efTemplate = fbReport.template();
		efCell = fbReport.cell();
				
		comparatorTemplate = new IoReportTemplateComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportTemplateComparator.Type.position);
		comparatorCell = new IoReportCellComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportCellComparator.Type.position);

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
		templates = fReport.all(fbReport.getClassTemplate());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbReport.getClassTemplate(),templates));}
//		Collections.sort(templates,comparatorTemplate);
	}
	
	public void addTemplate()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbReport.getClassTemplate()));}
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
		template = fReport.find(fbReport.getClassTemplate(), template);
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
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbReport.getClassCell()));}
		cell = efCell.build(template);
		cell.setName(efLang.createEmpty(langs));
		cell.setDescription(efDescription.createEmpty(langs));
		reset(false,false);
	}
	
	public void selectCell()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(cell));}
		cell = fReport.find(fbReport.getClassCell(), cell);
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