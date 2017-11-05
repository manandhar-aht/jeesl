package org.jeesl.factory.ejb.system.io.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportLayout;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportQueryType;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Size;
import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class EjbIoReportColumnFactory<L extends UtilsLang,D extends UtilsDescription,
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
								STYLE extends JeeslReportStyle<L,D>,CDT extends UtilsStatus<CDT,L,D>,
								CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends UtilsStatus<TLS,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportColumnFactory.class);
	
	final Class<COLUMN> cColumn;
	final Class<CDT> cDataType;
	final Class<CW> cColumnWidth;
	
	private final JeeslDbLangUpdater<COLUMN,L> dbuLang;
	private final JeeslDbDescriptionUpdater<COLUMN,D> dbuDescription;
    
	public EjbIoReportColumnFactory(final Class<L> cL,final Class<D> cD,final Class<COLUMN> cColumn, Class<CDT> cDataType, Class<CW> cColumnWidth)
	{       
        this.cColumn = cColumn;
        this.cDataType=cDataType;
        this.cColumnWidth=cColumnWidth;
        dbuLang = JeeslDbLangUpdater.factory(cColumn,cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cColumn,cD);
	}
	    
	public COLUMN build(GROUP group, List<COLUMN> list)
	{
		COLUMN ejb = null;
		try
		{
			ejb = cColumn.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setGroup(group);
			
			ejb.setVisible(false);
			ejb.setShowLabel(true);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public COLUMN build(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, GROUP group, XlsColumn column) throws UtilsNotFoundException
	{
		COLUMN ejb = null;
		try
		{
			ejb = cColumn.newInstance();
			ejb.setCode(column.getCode());
			ejb.setGroup(group);
			ejb = update(fReport,group,ejb,column);

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
	
	public COLUMN update(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, GROUP group, COLUMN eColumn, XlsColumn xColumn) throws UtilsNotFoundException
	{
		CDT eDataType = null;if(xColumn.getDataType()!=null){eDataType = fReport.fByCode(cDataType, xColumn.getDataType().getCode());}
		eColumn.setGroup(group);
		eColumn.setDataType(eDataType);
		
		eColumn.setPosition(xColumn.getPosition());
		eColumn.setVisible(xColumn.isVisible());
		
		eColumn.setShowLabel(xColumn.isShowLabel());
		eColumn.setShowWeb(xColumn.isSetShowWeb() && xColumn.isShowWeb());
		
		if(xColumn.isSetQueries())
		{
			try{eColumn.setQueryHeader(ReportXpath.getQuery(JeeslReportQueryType.Column.header.toString(), xColumn.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eColumn.setQueryHeader(null);}
			
			try{eColumn.setQueryCell(ReportXpath.getQuery(JeeslReportQueryType.Column.cell.toString(), xColumn.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eColumn.setQueryCell(null);}
			
			try{eColumn.setQueryFooter(ReportXpath.getQuery(JeeslReportQueryType.Column.footer.toString(), xColumn.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eColumn.setQueryFooter(null);}
		}
		if(xColumn.isSetLayout())
		{
			if(xColumn.getLayout().isSetSize())
			{
				try
				{
					Size size = ReportXpath.getSize(JeeslReportLayout.Code.columnWidth.toString(), xColumn.getLayout());
					eColumn.setColumWidth(fReport.fByCode(cColumnWidth, size.getType().getCode()));
					eColumn.setColumSize(size.getValue());
				}
				catch (ExlpXpathNotFoundException e)
				{
					eColumn.setColumWidth(null);
					eColumn.setColumSize(null);
				}
			}
		}
		return eColumn;
	}
	
	public COLUMN updateLD(UtilsFacade fUtils, COLUMN eColumn, XlsColumn xColumn) throws UtilsConstraintViolationException, UtilsLockingException
	{
		eColumn = dbuLang.handle(fUtils, eColumn, xColumn.getLangs());
		eColumn = fUtils.save(eColumn);
		eColumn = dbuDescription.handle(fUtils, eColumn, xColumn.getDescriptions());
		eColumn = fUtils.save(eColumn);
		return eColumn;
	}
	
	public List<COLUMN> toListVisibleColumns(SHEET sheet) {return toListVisibleColumns(sheet,null);}

	public List<COLUMN> toListVisibleColumns(SHEET sheet, Map<GROUP,Boolean> mapGroupVisibilityToggle)
	{
		List<COLUMN> list = new ArrayList<COLUMN>();
		for(GROUP g : sheet.getGroups())
		{			
			if(EjbIoReportColumnGroupFactory.visible(g,mapGroupVisibilityToggle))
			{
				for(COLUMN c : g.getColumns())
				{
					if(c.isVisible()){list.add(c);}
				}
			}
		}
		return list;
	}
	
	public  List<COLUMN> toListVisibleColumns(GROUP group)
	{
		List<COLUMN> list = new ArrayList<COLUMN>();
		for(COLUMN c : group.getColumns())
		{
			if(c.isVisible()){list.add(c);}
		}
		return list;
	}
	
	public CDT toCellDataType(COLUMN column)
	{
		if(column.getDataType()!=null){return column.getDataType();}
		logger.warn("No CellDataType for "+column.toString());
		return null;
	}
	
	public CDT toRowDataType(ROW row)
	{
		if(row.getDataType()!=null){return row.getDataType();}
		return null;
	}
	
	public boolean hasFooter(COLUMN column)
	{
		return (column.getQueryFooter()!=null && column.getQueryFooter().trim().length()>0);
	}
}