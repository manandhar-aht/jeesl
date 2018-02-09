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
	protected String calendarFormat;public String getCalendarFormat(){return calendarFormat;}
	protected String dateTimePattern; public String getDateTimePattern(){return dateTimePattern;}
	
	protected String paginatorPosition; public String getPaginatorPosition() {return paginatorPosition;}
	protected String paginatorTemplate; @Override public String getPaginatorTemplate() {return paginatorTemplate;}
	protected String rowsPerPageTemplate; @Override public String getRowsPerPageTemplate(){return rowsPerPageTemplate;}
	
	public AbstractSettingsBean()
	{
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		positionMenu2="right";
		positionSideMenu="right";
		calendarFormat = "dd.MM.yyyy";
		dateTimePattern = "dd.MM.yyyy HH:mm:ss";
		paginatorTemplate = "{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}";
		paginatorPosition = "bottom";
		rowsPerPageTemplate = "5,20,50,100";
		logger.info(AbstractLogMessage.postConstruct(ptt));
	}
}