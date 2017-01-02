package org.jeesl.interfaces.controller.report;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslPivotFactory <L extends UtilsLang, D extends UtilsDescription, A extends UtilsStatus<A,L,D>>
{		
	String buildTreeLevelName(String localeCode, EjbWithId ejb);
	int getIndexFor(A aggregation);
//	List<Finance> buildFinance(JeeslPivotAggregator dpa, List<EjbWithId> path);
}