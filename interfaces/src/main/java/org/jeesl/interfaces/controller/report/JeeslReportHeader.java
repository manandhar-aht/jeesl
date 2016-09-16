package org.jeesl.interfaces.controller.report;

import java.util.Map;

public interface JeeslReportHeader extends JeeslReport
{		
	Map<Long,String> getMapAggregationLabels();
}