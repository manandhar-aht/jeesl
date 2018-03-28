package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

public interface JeeslJsonReport <REPORT extends JeeslIoReport<?,?,?,?>>
			extends JeeslReport
{		
	public String getJsonStream() throws Exception;
	public void buildJson();
}