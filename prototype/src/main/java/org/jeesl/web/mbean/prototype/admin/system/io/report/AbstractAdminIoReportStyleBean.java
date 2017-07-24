package org.jeesl.web.mbean.prototype.admin.system.io.report;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportStyleFactory;
import org.jeesl.factory.factory.ReportFactoryFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportStyleComparator;
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

public class AbstractAdminIoReportStyleBean <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoReportStyleBean.class);
	
	protected JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport;
	
	private Class<STYLE> cStyle;
	
	private List<STYLE> styles; public List<STYLE> getStyles() {return styles;}
	
	private STYLE style; public STYLE getStyle() {return style;} public void setStyle(STYLE style) {this.style = style;}

	private Comparator<STYLE> comparatorStyle;
	
	private EjbIoReportStyleFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efStyle;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<REPORT> cReport, Class<IMPLEMENTATION> cImplementation, Class<WORKBOOK> cWorkbook, Class<SHEET> cSheet, Class<GROUP> cGroup, Class<COLUMN> cColumn, Class<ROW> cRow, Class<TEMPLATE> cTemplate, Class<CELL> cCell, Class<STYLE> cStyle, Class<CDT> cDataType, Class<CW> cColumnWidth, Class<RT> cRowType, Class<RC> cRevisionCategory)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fReport=fReport; 
		this.cStyle = cStyle;
		
		ReportFactoryFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> ff = ReportFactoryFactory.factory(cLang,cDescription,cCategory,cReport,cImplementation,cWorkbook,cSheet,cGroup,cColumn,cRow,cTemplate,cCell,cStyle,cDataType,cColumnWidth,cRowType);
		efStyle = ff.style();
				
		comparatorStyle = new IoReportStyleComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportStyleComparator.Type.position);

		reloadStyles();
	}
	
	private void reset(boolean rStyle)
	{
		if(rStyle){style=null;}
	}
	
	//*************************************************************************************
	private void reloadStyles()
	{
		styles = fReport.all(cStyle);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cStyle,styles));}
//		Collections.sort(templates,comparatorTemplate);
	}
	
	public void addStyle()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cStyle));}
		style = efStyle.build();
		style.setName(efLang.createEmpty(langs));
		style.setDescription(efDescription.createEmpty(langs));
		reset(false);
	}
	
	private void reloadStyle()
	{
		style = fReport.find(cStyle,style);
	}
	
	public void selectStyle() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(style));}
		style = fReport.find(cStyle, style);
		style = efLang.persistMissingLangs(fReport,langs,style);
		style = efDescription.persistMissingLangs(fReport,langs,style);
		
		reloadStyle();
		reset(false);
	}
	
	public void saveStyle() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(style));}
		style = fReport.save(style);
		reloadStyles();
		reloadStyle();
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
	
	public void cancelStyles() {reset(true);}
	 
	//*************************************************************************************
	protected void reorderStyles() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fReport,styles);Collections.sort(styles,comparatorStyle);}
	
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