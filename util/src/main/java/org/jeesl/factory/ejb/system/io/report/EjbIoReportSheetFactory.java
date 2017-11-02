package org.jeesl.factory.ejb.system.io.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.factory.builder.system.ReportFactoryBuilder;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
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
import net.sf.ahtutils.xml.report.Queries;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class EjbIoReportSheetFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
								SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
								GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								STYLE extends JeeslReportStyle<L,D>,
								CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends UtilsStatus<TLS,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportSheetFactory.class);
	
	private final EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efColumn;
	final Class<IMPLEMENTATION> cImplementation;
	final Class<SHEET> cSheet;
    
	private JeeslDbLangUpdater<SHEET,L> dbuLang;
	private JeeslDbDescriptionUpdater<SHEET,D> dbuDescription;
	
	public EjbIoReportSheetFactory(final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,?,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport, final Class<L> cL,final Class<D> cD,final Class<IMPLEMENTATION> cImplementation,final Class<SHEET> cSheet)
	{
		this.cImplementation=cImplementation;
        this.cSheet = cSheet;
        
        efColumn = fbReport.column();
        
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
	
	public SHEET build(UtilsFacade fReport, WORKBOOK workbook, XlsSheet sheet) throws UtilsNotFoundException
	{
		SHEET ejb = null;
		try
		{
			ejb = cSheet.newInstance();
			ejb.setCode(sheet.getCode());
			ejb.setWorkbook(workbook);
			ejb = update(fReport,ejb,sheet);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public SHEET update(UtilsFacade fReport, SHEET eSheet, XlsSheet xSheet) throws UtilsNotFoundException
	{
		try {eSheet.setImplementation(fReport.fByCode(cImplementation, ReportXpath.getImplementation(xSheet).getCode()));}
		catch (ExlpXpathNotFoundException e) {throw new UtilsNotFoundException(e.getMessage());}
		
		eSheet.setPosition(xSheet.getPosition());
		eSheet.setVisible(xSheet.isVisible());
		
		try
		{
			Queries queries = ReportXpath.getQueries(xSheet);
			try{eSheet.setQueryTable(ReportXpath.getQuery(JeeslReportQueryType.Sheet.table.toString(), queries).getValue());}
			catch (ExlpXpathNotFoundException e) {eSheet.setQueryTable(null);}
		}
		catch (ExlpXpathNotFoundException e) {}
		
		
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
	
	
	public boolean hasFooters(SHEET sheet){return hasFooters(sheet,null);}
	public boolean hasFooters(SHEET sheet, Map<GROUP,Boolean> mapGroupVisibilityToggle)
	{
		boolean withFooter = false;
		{
			for(COLUMN c : efColumn.toListVisibleColumns(sheet,mapGroupVisibilityToggle))
			{
				if(EjbIoReportColumnFactory.hasFooter(c)){withFooter=true;}
			}
		}
		return withFooter;
	}
	
	public static <WORKBOOK extends JeeslReportWorkbook<?,SHEET>,
					SHEET extends JeeslReportSheet<?,?,?,WORKBOOK,?,?>>
				List<SHEET> toListVisibleShets(WORKBOOK workbook,Map<SHEET,Boolean> mapVisibilityToggle)
	{
		List<SHEET> list = new ArrayList<SHEET>();
		for(SHEET g : workbook.getSheets())
		{
			if(visible(g,mapVisibilityToggle))
			{
				list.add(g);
			}
		}
		return list;
	}
	
	private static <SHEET extends JeeslReportSheet<?,?,?,?,?,?>>
			boolean visible(SHEET s, Map<SHEET,Boolean> mapVisibilityToggle)
	{
		boolean toggle = true;
		if(mapVisibilityToggle!=null)
		{
			if(mapVisibilityToggle.containsKey(s)){toggle = mapVisibilityToggle.get(s);}
			else{toggle = false;}
		}
		return s.isVisible() && toggle;
	}
}