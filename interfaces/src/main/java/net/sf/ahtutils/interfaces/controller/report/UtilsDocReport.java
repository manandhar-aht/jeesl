package net.sf.ahtutils.interfaces.controller.report;

import java.io.InputStream;

public interface UtilsDocReport {
	
public static String mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	
	public InputStream docStream() throws Exception;
	public String docFileName();
}
