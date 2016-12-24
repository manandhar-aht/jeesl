package org.jeesl.factory.ejb.system.io.report;

import java.util.UUID;

import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
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
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class EjbIoReportSheetFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportSheetFactory.class);
	
	final Class<SHEET> cSheet;
    
	private JeeslDbLangUpdater<SHEET,L> dbuLang;
	private JeeslDbDescriptionUpdater<SHEET,D> dbuDescription;
	
	public EjbIoReportSheetFactory(final Class<L> cL,final Class<D> cD,final Class<SHEET> cSheet)
	{       
        this.cSheet = cSheet;
        
        dbuLang = JeeslDbLangUpdater.factory(cSheet, cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cSheet, cD);
	}
	    
	public SHEET build(WORKBOOK workbook)
	{
		SHEET ejb = null;
		try
		{
			ejb = cSheet.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setWorkbook(workbook);
			ejb.setPosition(1);
			ejb.setVisible(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public SHEET build(WORKBOOK workbook, XlsSheet sheet)
	{
		SHEET ejb = null;
		try
		{
			ejb = cSheet.newInstance();
			ejb.setCode(sheet.getCode());
			ejb.setWorkbook(workbook);
			ejb = update(ejb,sheet);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public SHEET update(SHEET eSheet, XlsSheet xSheet)
	{
		eSheet.setPosition(xSheet.getPosition());
		eSheet.setVisible(xSheet.isVisible());
		return eSheet;
	}
	
	public SHEET updateLD(UtilsFacade fUtils, SHEET eSheet, XlsSheet xSheet) throws UtilsConstraintViolationException, UtilsLockingException, ExlpXpathNotFoundException
	{
		eSheet=dbuLang.handle(fUtils, eSheet, ReportXpath.getLangs(xSheet));
		eSheet = fUtils.save(eSheet);
		
		eSheet=dbuDescription.handle(fUtils, eSheet, ReportXpath.getDescriptions(xSheet));
		eSheet = fUtils.save(eSheet);
		return eSheet;
	}
}