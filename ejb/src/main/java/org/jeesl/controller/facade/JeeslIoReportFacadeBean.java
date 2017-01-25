package org.jeesl.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslIoReportFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
									WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									STYLE extends JeeslReportStyle<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
									CDT extends UtilsStatus<CDT,L,D>,CW extends UtilsStatus<CW,L,D>,
									RT extends UtilsStatus<RT,L,D>,
									ENTITY extends EjbWithId,
									ATTRIBUTE extends EjbWithId,
									FILLING extends UtilsStatus<FILLING,L,D>,
									TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,FILLING,TRANSFORMATION>
{	
	private final Class<CATEGORY> cCategory;
	private final Class<REPORT> cReport;
	private final Class<WORKBOOK> cWorkbook;
	private final Class<SHEET> cSheet;
	private final Class<GROUP> cGroup;
	private final Class<COLUMN> cColumn;
	private final Class<ROW> cRow;
	private final Class<TEMPLATE> cTemplate;
	private final Class<CELL> cCell;
	
	public JeeslIoReportFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CELL> cCell)
	{
		super(em);
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cWorkbook=cWorkbook;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
		this.cColumn=cColumn;
		this.cRow=cRow;
		this.cTemplate=cTemplate;
		this.cCell=cCell;
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
		sheet.getRows().size();
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
	
	@Override public TEMPLATE load(TEMPLATE template)
	{
		template = em.find(cTemplate, template.getId());
		template.getCells().size();
		return template;
	}
	
	@Override public SHEET fSheet(WORKBOOK workbook, String code) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SHEET> cQ = cB.createQuery(cSheet);
        
		Root<SHEET> sheet = cQ.from(cSheet);
        
        Path<WORKBOOK> pWorkbook = sheet.get(JeeslReportSheet.Attributes.workbook.toString());
        Expression<String> eCode = sheet.get(JeeslReportSheet.Attributes.code.toString());
       
        CriteriaQuery<SHEET> select = cQ.select(sheet);
	    select.where(cB.and(cB.equal(pWorkbook,workbook),cB.equal(eCode,code)));

		TypedQuery<SHEET> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+cSheet.getSimpleName()+" workbook:"+workbook.toString()+" code:"+code);}
	}
	
	@Override public void rmSheet(SHEET sheet) throws UtilsConstraintViolationException
	{
		sheet = em.find(cSheet, sheet.getId());
		sheet.getWorkbook().getSheets().remove(sheet);
		this.rmProtected(sheet);
	}
	
	@Override public void rmGroup(GROUP group) throws UtilsConstraintViolationException
	{
		group = em.find(cGroup, group.getId());
		group.getSheet().getGroups().remove(group);
		this.rmProtected(group);
	}
	
	@Override public void rmColumn(COLUMN column) throws UtilsConstraintViolationException
	{
		column = em.find(cColumn, column.getId());
		column.getGroup().getColumns().remove(column);
		this.rmProtected(column);
	}
	
	@Override public void rmRow(ROW row) throws UtilsConstraintViolationException
	{
		row = em.find(cRow, row.getId());
		row.getSheet().getRows().remove(row);
		this.rmProtected(row);
	}
	
	@Override public void rmCell(CELL cell) throws UtilsConstraintViolationException
	{
		cell = em.find(cCell, cell.getId());
		cell.getTemplate().getCells().remove(cell);
		this.rmProtected(cell);
	}
	
	@Override public List<REPORT> fReports(List<CATEGORY> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<CATEGORY>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cReport,ppCategory);
	}
}