package org.jeesl.interfaces.model.system.io.report;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportWorkbook<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									REPORT extends JeeslReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
									COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	REPORT getReport();
	void setReport(REPORT report);
	
	List<SHEET> getSheets();
	void setSheets(List<SHEET> sheets);
}