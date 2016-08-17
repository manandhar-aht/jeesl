package org.jeesl.interfaces.controller.report;

import java.io.InputStream;

public interface JeeslPdfReport extends JeeslReport
{	
	public static String mimeType = "application/pdf";
	
	public InputStream pdfStream() throws Exception;
	public String pdfFileName();
}