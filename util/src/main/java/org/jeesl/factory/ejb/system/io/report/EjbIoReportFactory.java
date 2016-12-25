package org.jeesl.factory.ejb.system.io.report;

import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Report;

public class EjbIoReportFactory<L extends UtilsLang,D extends UtilsDescription,
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
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportFactory.class);
	
	final Class<REPORT> cReport;
	
	private JeeslDbLangUpdater<REPORT,L> dbuReportLang;
	private JeeslDbDescriptionUpdater<REPORT,D> dbuReportDescription;
    
	public EjbIoReportFactory(final Class<L> cL,final Class<D> cD,final Class<REPORT> cReport)
	{       
        this.cReport = cReport;
        
		dbuReportLang = JeeslDbLangUpdater.factory(cReport, cL);
		dbuReportDescription = JeeslDbDescriptionUpdater.factory(cReport, cD);
	}
	    
	public REPORT build(CATEGORY category)
	{
		REPORT ejb = null;
		try
		{
			ejb = cReport.newInstance();
			ejb.setCategory(category);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public REPORT build(CATEGORY category, IMPLEMENTATION eImplementation, Report xReport)
	{
		REPORT ejb = null;
		try
		{
			ejb = cReport.newInstance();
			ejb.setCode(xReport.getCode());
			ejb = update(ejb,xReport,category,eImplementation);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public REPORT update (REPORT eReport, Report xReport, CATEGORY eCategory, IMPLEMENTATION eImplementation)
	{
		eReport.setCategory(eCategory);
		eReport.setImplementation(eImplementation);
		eReport.setPosition(xReport.getPosition());
		eReport.setVisible(xReport.isVisible());
		return eReport;
	}
	
	public REPORT updateLD(UtilsFacade fUtils, REPORT eReport, Report xReport) throws UtilsConstraintViolationException, UtilsLockingException
	{
		eReport=dbuReportLang.handle(fUtils, eReport, xReport.getLangs());
		eReport = fUtils.save(eReport);
		
		eReport=dbuReportDescription.handle(fUtils, eReport, xReport.getDescriptions());
		eReport = fUtils.save(eReport);
		
		return eReport;
	}
}