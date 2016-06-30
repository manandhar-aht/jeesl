package net.sf.ahtutils.model.primefaces.stream;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import net.sf.ahtutils.jsf.interfaces.AhtStreamedContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XlsStreamedContent implements AhtStreamedContent, Serializable
{	
	final static Logger logger = LoggerFactory.getLogger(XlsStreamedContent.class);
	private static final long serialVersionUID = 1L;

	private InputStream stream;
	private String contentType;
	private String name;
	private Date record;
		
	public XlsStreamedContent(InputStream stream, String name)
	{
		this.contentType = "application/msexcel";
		record = new Date();
		this.stream = stream;
		this.name = name;
	}
	
	@Override
	public String getContentEncoding()
	{
		logger.warn("The method getContentEncoding() must be overriden since PF 3.5");
		logger.warn("Returning null here. This is UNTESTED!!");
		return null;
	}

	public InputStream getStream() {return stream;}
	public void setStream(InputStream stream) {this.stream = stream;}
	
	public String getContentType() {return contentType;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
}