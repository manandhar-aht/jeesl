package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslSettingsBean;
import org.jeesl.controller.monitor.ProcessingTimeTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSettingsBean implements Serializable,JeeslSettingsBean
{
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsBean.class);
	private static final long serialVersionUID = 1L;
	
	protected String positionSideMenu;public String getPositionSideMenu() {return positionSideMenu;}
	protected String positionMenu2;public String getPositionMenu2() {return positionMenu2;}
	protected String calendarFormat; public String getCalendarFormat(){return calendarFormat;}
	protected String timeFormat; public String getTimeFormat(){return timeFormat;}
	
	protected String patternDate; public String getPatternDate() {return patternDate;}
	protected String patternHour; public String getPatternHour() {return patternHour;}
	
	protected String allowUploadSvg; public String getAllowUploadSvg() {return allowUploadSvg;}
	protected String allowUploadJesslGraphicType; public String getAllowUploadJesslGraphicType() {return allowUploadJesslGraphicType;}
	
	protected String paginatorPosition; public String getPaginatorPosition() {return paginatorPosition;}
	protected String paginatorTemplate; @Override public String getPaginatorTemplate() {return paginatorTemplate;}
	protected String rowsPerPageTemplate; @Override public String getRowsPerPageTemplate(){return rowsPerPageTemplate;}
	
	protected String filterStyle; public String getFilterStyle() {return filterStyle;}

	public AbstractSettingsBean()
	{
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		positionMenu2="right";
		positionSideMenu="right";
		calendarFormat = "dd.MM.yyyy";
		timeFormat = "dd.MM.yyyy HH:mm";
		initCalendarPattern();
		paginatorTemplate = "{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}";
		paginatorPosition = "bottom";
		rowsPerPageTemplate = "5,20,50,100";
		filterStyle = "width: 50px;";
		
		allowUploadSvg = "/(\\.|\\/)(svg)$/";
		allowUploadJesslGraphicType = "/(\\.|\\/)(svg|png)$/";
		
		logger.info(AbstractLogMessage.postConstruct(ptt));
	}
	
	protected String datePattern; @Override public String getDatePattern(){return datePattern;}
	protected String dateTimePattern; @Override public String getDateTimePattern(){return dateTimePattern;}
	
	private void initCalendarPattern()
	{
		patternDate = "dd.MM.yyyy";
		patternHour = "dd.MM.yyyy HH:mm";
		
//		Deprecated below
		datePattern = "dd.MM.yyyy";
		dateTimePattern = "dd.MM.yyyy HH:mm:ss";
	}
}