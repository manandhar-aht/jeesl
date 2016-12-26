package org.jeesl.factory.ejb.system.io.report;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportQueryType;
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
import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class EjbIoReportColumnFactory<L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportColumnFactory.class);
	
	final Class<COLUMN> cColumn;
	
	private JeeslDbLangUpdater<COLUMN,L> dbuLang;
	private JeeslDbDescriptionUpdater<COLUMN,D> dbuDescription;
    
	public EjbIoReportColumnFactory(final Class<L> cL,final Class<D> cD,final Class<COLUMN> cColumn)
	{       
        this.cColumn = cColumn;
        dbuLang = JeeslDbLangUpdater.factory(cColumn, cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cColumn, cD);
	}
	    
	public COLUMN build(GROUP group)
	{
		COLUMN ejb = null;
		try
		{
			ejb = cColumn.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setGroup(group);
			ejb.setPosition(1);
			ejb.setVisible(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public COLUMN build(GROUP group, XlsColumn column)
	{
		COLUMN ejb = null;
		try
		{
			ejb = cColumn.newInstance();
			ejb.setCode(column.getCode());
			ejb.setGroup(group);
			ejb = update(ejb,column);

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
	
	public COLUMN update(COLUMN eColumn, XlsColumn xColumn)
	{
		eColumn.setPosition(xColumn.getPosition());
		eColumn.setVisible(xColumn.isVisible());
		
		if(xColumn.isSetQueries())
		{
			try{eColumn.setQueryHeader(ReportXpath.getQuery(JeeslReportQueryType.Column.header.toString(), xColumn.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eColumn.setQueryHeader(null);}
			
			try{eColumn.setQueryCell(ReportXpath.getQuery(JeeslReportQueryType.Column.cell.toString(), xColumn.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eColumn.setQueryCell(null);}
			
			try{eColumn.setQueryFooter(ReportXpath.getQuery(JeeslReportQueryType.Column.footer.toString(), xColumn.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eColumn.setQueryFooter(null);}
		}
		
		return eColumn;
	}
	
	public COLUMN updateLD(UtilsFacade fUtils, COLUMN eColumn, XlsColumn xColumn) throws UtilsConstraintViolationException, UtilsLockingException
	{
		eColumn=dbuLang.handle(fUtils, eColumn, xColumn.getLangs());
		eColumn = fUtils.save(eColumn);
		eColumn=dbuDescription.handle(fUtils, eColumn, xColumn.getDescriptions());
		eColumn = fUtils.save(eColumn);
		return eColumn;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
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
		List<COLUMN> toListVisibleColumns(SHEET sheet)
	{
		List<COLUMN> list = new ArrayList<COLUMN>();
		for(GROUP g : sheet.getGroups())
		{
			if(g.isVisible())
			{
				for(COLUMN c : g.getColumns())
				{
					if(c.isVisible()){list.add(c);}
				}
			}
		}
		return list;
	}
}