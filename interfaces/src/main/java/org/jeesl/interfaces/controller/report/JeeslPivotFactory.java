package org.jeesl.interfaces.controller.report;

import java.util.List;

import net.sf.ahtutils.interfaces.controller.report.JeeslPivotAggregator;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.finance.Finance;

public interface JeeslPivotFactory <L extends UtilsLang, D extends UtilsDescription, A extends UtilsStatus<A,L,D>>
{		
	String buildTreeLevelName(String localeCode, EjbWithId ejb);
	int getIndexFor(A aggregation);
	List<Finance> buildFinance(JeeslPivotAggregator dpa, List<EjbWithId> path);
	List<Finance> buildFinance(JeeslPivotAggregator dpa, List<EjbWithId> path, List<EjbWithId> last);
}