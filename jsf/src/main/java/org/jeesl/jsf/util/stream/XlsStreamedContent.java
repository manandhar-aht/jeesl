package org.jeesl.jsf.util.stream;

import java.io.InputStream;

import org.jeesl.interfaces.controller.report.JeeslXlsReport;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class XlsStreamedContent <REPORT extends JeeslIoReport<?,?,?,?>> extends DefaultStreamedContent implements StreamedContent 
{
	public XlsStreamedContent(JeeslXlsReport<REPORT> report) throws Exception
	{
		this(report.xlsStream(),JeeslXlsReport.mimeType,report.xlsFileName());
	}
	
	public XlsStreamedContent(InputStream is, String mimeType, String fileName)
	{
		super(is,mimeType,fileName);
	}
	
	public XlsStreamedContent(InputStream is, String fileName)
	{
		this(is,JeeslXlsReport.mimeType,fileName);
	}
}