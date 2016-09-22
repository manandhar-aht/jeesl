package org.jeesl.exception;

import java.io.Serializable;
import java.util.Date;

public class JeeslNotUniqueException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;
	
	private boolean withDetails;
	private Date when;

	private String whereKey,whereDetail;
	private String whatKey,whatDetail;

	public JeeslNotUniqueException(String s)
	{ 
		super(s);
		withDetails=false;
	}
	
	public JeeslNotUniqueException() 
	{
		super("Something is not found, additional infos set in extended attributes of "+JeeslNotUniqueException.class.getSimpleName());
		when = new Date();
		withDetails=true;
	}
	
	public String toHash()
	{
		return whereKey+"-"+whatKey;
	}
	
	public boolean isWithDetails() {return withDetails;}
	public Date getWhen() {return when;}
	
	public String getWhereKey() {return whereKey;}
	public void setWhereKey(String whereKey) {this.whereKey = whereKey;}

	public String getWhereDetail() {return whereDetail;}
	public void setWhereDetail(String whereDetail) {this.whereDetail = whereDetail;}

	public String getWhatKey() {return whatKey;}
	public void setWhatKey(String whatKey) {this.whatKey = whatKey;}

	public String getWhatDetail() {return whatDetail;}
	public void setWhatDetail(String whatDetail) {this.whatDetail = whatDetail;}
	
}
