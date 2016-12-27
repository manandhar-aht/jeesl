package org.jeesl.controller.handler.ui.helper;

import java.io.Serializable;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UiHelperIoReport <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
										WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
										RT extends UtilsStatus<RT,L,D>,
										ENTITY extends EjbWithId,
										ATTRIBUTE extends EjbWithId,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiHelperIoReport.class);
		
	private REPORT report;
	private SHEET sheet;
	private GROUP group;
	
	private boolean showPanelReport; public boolean isShowPanelReport() {return showPanelReport;}
	private boolean showPanelSheet; public boolean isShowPanelSheet() {return showPanelSheet;}
	private boolean showPanelGroup; public boolean isShowPanelGroup() {return showPanelGroup;}

	public UiHelperIoReport()
	{
		showPanelReport = false;
		showPanelSheet = false;
		showPanelGroup = false;
	}
	
	public void check(REPORT report)
	{
		if(report!=null && EjbIdFactory.isUnSaved(report)){showPanelReport=true;}
		else if(this.report==null){showPanelReport = false;}
		else if(this.report!=null && report!=null) {showPanelReport = this.report.equals(report);}
		else {showPanelReport = false;}
		this.report=report;
	}
	
	public void check(SHEET sheet)
	{
		if(sheet!=null && EjbIdFactory.isUnSaved(sheet)){showPanelSheet=true;}
		else if(this.sheet==null){showPanelSheet = false;}
		else if(this.sheet!=null && sheet!=null) {showPanelSheet = this.sheet.equals(sheet);}
		else {showPanelSheet = false;}
		this.sheet=sheet;
	}
	
	public void check(GROUP group)
	{
		if(group!=null && EjbIdFactory.isUnSaved(group)){showPanelGroup=true;}
		else if(this.group==null){showPanelGroup = false;}
		else if(this.group!=null && group!=null) {showPanelGroup = this.group.equals(group);}
		else {showPanelGroup = false;}
		this.group=group;
	}
}