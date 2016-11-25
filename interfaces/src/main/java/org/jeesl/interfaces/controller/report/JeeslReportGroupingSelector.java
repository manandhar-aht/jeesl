package org.jeesl.interfaces.controller.report;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslReportGroupingSelector <GROUPING extends UtilsStatus<GROUPING,L,D>, L extends UtilsLang,D extends UtilsDescription> extends JeeslReport
{		
	void setReportGrouping(GROUPING grouping);
}