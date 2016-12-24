package org.jeesl.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslIoReportFacadeBean<L extends UtilsLang,D extends UtilsDescription,
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
					extends UtilsFacadeBean
					implements JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>
{	
	private final Class<CATEGORY> cCategory;
	private final Class<REPORT> cReport;
	private final Class<WORKBOOK> cWorkbook;
	private final Class<SHEET> cSheet;
	private final Class<GROUP> cGroup;
	
	public JeeslIoReportFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup)
	{
		super(em);
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cWorkbook=cWorkbook;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
	}
	
	@Override public REPORT load(REPORT report, boolean recursive)
	{
		report = em.find(cReport, report.getId());
		if(report.getWorkbook()!=null)
		{
			report.getWorkbook().getSheets().size();
			
			if(recursive)
			{
				for(SHEET sheet : report.getWorkbook().getSheets())
				{
					for(GROUP group : sheet.getGroups()){group.getColumns().size();}
				}
			}
			
		}
		return report;
	}
	
	@Override public WORKBOOK load(WORKBOOK workbook)
	{
		workbook = em.find(cWorkbook, workbook.getId());
		workbook.getSheets().size();
		return workbook;
	}
	
	@Override public SHEET load(SHEET sheet, boolean recursive)
	{
		sheet = em.find(cSheet, sheet.getId());
		sheet.getGroups().size();
		if(recursive)
		{
			for(GROUP group : sheet.getGroups()){group.getColumns().size();}
		}
		return sheet;
	}
	
	@Override public GROUP load(GROUP group)
	{
		group = em.find(cGroup, group.getId());
		group.getColumns().size();
		return group;
	}
	
	@Override public List<REPORT> fReports(List<CATEGORY> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<CATEGORY>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cReport,ppCategory);
	}
}