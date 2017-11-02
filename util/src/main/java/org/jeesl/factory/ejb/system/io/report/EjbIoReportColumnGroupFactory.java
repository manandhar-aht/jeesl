package org.jeesl.factory.ejb.system.io.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import net.sf.ahtutils.xml.report.ColumnGroup;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class EjbIoReportColumnGroupFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
								SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
								GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								STYLE extends JeeslReportStyle<L,D>,CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends UtilsStatus<TLS,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportColumnGroupFactory.class);
	
	final Class<GROUP> cGroup;
	final Class<STYLE> cStyle;
	
	private JeeslDbLangUpdater<GROUP,L> dbuLang;
	private JeeslDbDescriptionUpdater<GROUP,D> dbuDescription;
    
	public EjbIoReportColumnGroupFactory(final Class<L> cL,final Class<D> cD,final Class<GROUP> cGroup, final Class<STYLE> cStyle)
	{       
        this.cGroup = cGroup;
        this.cStyle = cStyle;
        dbuLang = JeeslDbLangUpdater.factory(cGroup, cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cGroup, cD);
	}
	    
	public GROUP build(SHEET sheet)
	{
		GROUP ejb = null;
		try
		{
			ejb = cGroup.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setSheet(sheet);
			ejb.setPosition(1);
			ejb.setVisible(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public GROUP build(UtilsFacade fReport, SHEET sheet, ColumnGroup group) throws UtilsNotFoundException
	{
		GROUP ejb = null;
		try
		{
			ejb = cGroup.newInstance();
			ejb.setCode(group.getCode());
			ejb.setSheet(sheet);
			ejb = update(fReport,ejb,group);

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
	
	public GROUP update(UtilsFacade fReport, GROUP eGroup, ColumnGroup xGroup) throws UtilsNotFoundException
	{
		eGroup.setPosition(xGroup.getPosition());
		eGroup.setVisible(xGroup.isVisible());
		eGroup.setQueryColumns(xGroup.getQuery());
		
		eGroup.setShowLabel(xGroup.isShowLabel());
		eGroup.setShowWeb(xGroup.isSetShowWeb() && xGroup.isShowWeb());
		
		if(xGroup.isSetLayout())
		{
			if(xGroup.getLayout().isSetStyles())
			{
				try {eGroup.setStyleHeader(fReport.fByCode(cStyle, ReportXpath.getStyle(JeeslReportLayout.Style.header, xGroup.getLayout().getStyles()).getCode()));}
				catch (ExlpXpathNotFoundException e) {}
			}
		}
		
		return eGroup;
	}
	
	public GROUP updateLD(UtilsFacade fReport, GROUP eGroup, ColumnGroup xGroup) throws UtilsConstraintViolationException, UtilsLockingException
	{
		eGroup=dbuLang.handle(fReport, eGroup, xGroup.getLangs());
		eGroup = fReport.save(eGroup);
		eGroup=dbuDescription.handle(fReport, eGroup, xGroup.getDescriptions());
		eGroup = fReport.save(eGroup);
		return eGroup;
	}
	
	public Map<GROUP,Integer> toMapVisibleGroupSize(SHEET sheet)
	{
		Map<GROUP,Integer> map = new HashMap<GROUP,Integer>();
		for(GROUP g : sheet.getGroups())
		{
			int size=0;
			for(COLUMN c : g.getColumns())
			{
				if(c.isVisible()){size++;}
			}
			map.put(g,size);
		}
		return map;
	}
	
	public static <SHEET extends JeeslReportSheet<?,?,?,?,GROUP,?>,
					GROUP extends JeeslReportColumnGroup<?,?,SHEET,COLUMN,?>,
					COLUMN extends JeeslReportColumn<?,?,?,?,?,?,SHEET,GROUP,COLUMN,?,?,?,?,?,?,?,?,?,?,?>>
		Map<GROUP,List<COLUMN>> toMapVisibleGroupColumns(SHEET sheet)
	{
		Map<GROUP,List<COLUMN>> map = new HashMap<GROUP,List<COLUMN>>();
		for(GROUP g : sheet.getGroups())
		{
			if(g.isVisible())
			{
				List<COLUMN> list = new ArrayList<COLUMN>();
				for(COLUMN c : g.getColumns())
				{
					if(c.isVisible()){list.add(c);}
				}
				map.put(g,list);
			}
			
		}
		return map;
	}
	
	public static <SHEET extends JeeslReportSheet<?,?,?,?,GROUP,?>,
				GROUP extends JeeslReportColumnGroup<?,?,SHEET,?,?>>
		List<GROUP> toListVisibleGroups(SHEET sheet) {return toListVisibleGroups(sheet,null);}
	
	public static <
				SHEET extends JeeslReportSheet<?,?,?,?,GROUP,?>,
				GROUP extends JeeslReportColumnGroup<?,?,SHEET,?,?>>
		List<GROUP> toListVisibleGroups(SHEET sheet,Map<GROUP,Boolean> mapGroupVisibilityToggle)
	{
		List<GROUP> list = new ArrayList<GROUP>();
		for(GROUP g : sheet.getGroups())
		{
			if(EjbIoReportColumnGroupFactory.visible(g,mapGroupVisibilityToggle))
			{
				list.add(g);
			}
		}
		return list;
	}
	
	public static <GROUP extends JeeslReportColumnGroup<?,?,?,?,?>>
			boolean visible(GROUP g, Map<GROUP,Boolean> mapGroupVisibilityToggle)
	{
		boolean toggle = true;
		if(mapGroupVisibilityToggle!=null)
		{
			if(mapGroupVisibilityToggle.containsKey(g)){toggle = mapGroupVisibilityToggle.get(g);}
			else{toggle = false;}
		}
		
		return g.isVisible() && toggle;
	}
}