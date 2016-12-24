package org.jeesl.interfaces.facade;

import java.util.List;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoReportFacade <L extends UtilsLang,D extends UtilsDescription,
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
			extends UtilsFacade
{	
	REPORT load(REPORT report, boolean recursive);
	WORKBOOK load(WORKBOOK workbook);
	SHEET load(SHEET sheet, boolean recursive);
	GROUP load(GROUP group);
	
	List<REPORT> fReports(List<CATEGORY> categories, boolean showInvisibleEntities);
}