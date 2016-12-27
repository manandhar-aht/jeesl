package org.jeesl.factory.ejb.system.io.report;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import net.sf.ahtutils.xml.report.Row;

public class EjbIoReportRowFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportRowFactory.class);
	
	final Class<ROW> cRow;
	
	private JeeslDbLangUpdater<ROW,L> dbuLang;
	private JeeslDbDescriptionUpdater<ROW,D> dbuDescription;
    
	public EjbIoReportRowFactory(final Class<L> cL,final Class<D> cD,final Class<ROW> cRow)
	{       
        this.cRow = cRow;
        dbuLang = JeeslDbLangUpdater.factory(cRow, cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cRow, cD);
	}
	    
	public ROW build(SHEET sheet)
	{
		ROW ejb = null;
		try
		{
			ejb = cRow.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setSheet(sheet);
			ejb.setPosition(1);
			ejb.setVisible(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public ROW build(SHEET sheet, Row row, RT eRowType)
	{
		ROW ejb = null;
		try
		{
			ejb = cRow.newInstance();
			ejb.setCode(sheet.getCode());
			ejb.setSheet(sheet);
			ejb = update(ejb,row,eRowType);

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
		
	public ROW update(ROW eRow, Row xRow, RT eRowType)
	{
		eRow.setPosition(xRow.getPosition());
		eRow.setVisible(xRow.isVisible());
		eRow.setType(eRowType);
		return eRow;
	}
		
	public ROW updateLD(UtilsFacade fUtils, ROW eRow, Row xRow) throws UtilsConstraintViolationException, UtilsLockingException
	{
		eRow=dbuLang.handle(fUtils, eRow, xRow.getLangs());
		eRow = fUtils.save(eRow);
		eRow=dbuDescription.handle(fUtils, eRow, xRow.getDescriptions());
		eRow = fUtils.save(eRow);
		return eRow;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
				CATEGORY extends UtilsStatus<CATEGORY,L,D>,
				REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
				IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
				WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
				SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
				GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
				COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
				ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
				CDT extends UtilsStatus<CDT,L,D>,
				RT extends UtilsStatus<RT,L,D>,
				ENTITY extends EjbWithId,
				ATTRIBUTE extends EjbWithId,
				FILLING extends UtilsStatus<FILLING,L,D>,
				TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
		List<ROW> toListVisibleRows(SHEET sheet)
	{
		List<ROW> list = new ArrayList<ROW>();
		for(ROW r : sheet.getRows())
		{
			if(r.isVisible())
			{
				list.add(r);
			}
		}
		return list;
	}

}