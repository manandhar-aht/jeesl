package org.jeesl.interfaces.controller.report;

import java.io.InputStream;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

public interface JeeslPdfReport <REPORT extends JeeslIoReport<?,?,?,?>>
			extends JeeslReport
{	
	public static String mimeType = "application/pdf";
	
	public InputStream pdfStream() throws Exception;
	public String pdfFileName();
}