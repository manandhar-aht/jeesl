package org.jeesl.interfaces.controller.report;

import java.util.Map;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

public interface JeeslReportHeader <REPORT extends JeeslIoReport<?,?,?,?>>
			extends JeeslReport
{		
	Map<Long,String> getMapAggregationLabels();
}