package org.jeesl.interfaces.controller.report;

import java.io.InputStream;

public interface JeeslXlsReport extends JeeslReport
{		
	public static String mimeType = "application/msexcel";
	
	public InputStream xlsStream() throws Exception;
	public String xlsFileName();
}