package org.jeesl.model.pojo.exception;

import java.io.Serializable;
import java.util.Date;

public class DatatableException implements Serializable
{
	private static final long serialVersionUID = 1L;


	public DatatableException()
	{
		
	}
	
	private Date record;
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}

	private String type;
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	private String detail;
	public String getDetail() {return detail;}
	public void setDetail(String detail) {this.detail = detail;}
}