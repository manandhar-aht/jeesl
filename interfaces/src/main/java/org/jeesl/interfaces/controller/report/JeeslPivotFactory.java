package org.jeesl.interfaces.controller.report;

import java.util.List;

import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;

import net.sf.ahtutils.interfaces.controller.report.JeeslPivotAggregator;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.finance.Finance;

public interface JeeslPivotFactory <L extends UtilsLang, D extends UtilsDescription,
									A extends UtilsStatus<A,L,D>>
{	
	int getSizeAggregation();
	int getSizeValue();
	int getIndexFor(A aggregation);
	JeeslReportAggregationLevelFactory getTxtLevelFactory();
	List<Finance> buildFinance(JeeslPivotAggregator dpa, List<EjbWithId> path);
	List<Finance> buildFinance(JeeslPivotAggregator dpa, List<EjbWithId> path, List<EjbWithId> last);
}