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

public class JeeslIoReportFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									FILLING extends UtilsStatus<FILLING,L,D>,
									TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoReportFacade<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN,FILLING,TRANSFORMATION>
{	
	private final Class<CATEGORY> cCategory;
	private final Class<REPORT> cReport;
	private final Class<SHEET> cSheet;
	private final Class<GROUP> cGroup;
	
	public JeeslIoReportFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<SHEET> cSheet, final Class<GROUP> cGroup)
	{
		super(em);
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
	}
	
	@Override public REPORT load(REPORT report)
	{
		report = em.find(cReport, report.getId());
		if(report.getWorkbook()!=null)
		{
			report.getWorkbook().getSheets().size();
		}
		return report;
	}
	
	@Override public SHEET load(SHEET sheet)
	{
		sheet = em.find(cSheet, sheet.getId());
		sheet.getGroups().size();
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