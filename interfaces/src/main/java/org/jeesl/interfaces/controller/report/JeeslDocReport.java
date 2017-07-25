package org.jeesl.interfaces.controller.report;

import java.io.InputStream;

public interface JeeslDocReport
{
	public static String mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	
	public InputStream docStream() throws Exception;
	public String docFileName();
}