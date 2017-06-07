package org.jeesl.jsf.util.stream;

import java.io.InputStream;

import org.jeesl.interfaces.controller.report.JeeslPdfReport;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class PdfStreamedContent extends DefaultStreamedContent implements StreamedContent 
{
	public PdfStreamedContent(JeeslPdfReport report) throws Exception
	{
		this(report.pdfStream(),JeeslPdfReport.mimeType,report.pdfFileName());
	}
	
	public PdfStreamedContent(InputStream is, String mimeType, String fileName)
	{
		super(is,mimeType,fileName);
	}
}