package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

public interface JeeslZipReport <REPORT extends JeeslIoReport<?,?,?,?>>
			extends JeeslReport
{	
	public static String mimeType = "application/zip";
}