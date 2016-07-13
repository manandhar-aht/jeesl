package net.sf.ahtutils.model.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class JsonFlatFigure implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("G1")
	private String g1;
	public String getG1() {return g1;}
	public void setG1(String g1) {this.g1 = g1;}

	@JsonProperty("G2")
	private String g2;
	public String getG2() {return g2;}
	public void setG2(String g2) {this.g2 = g2;}
	
	@JsonProperty("D1")
	private String d1;
	public String getD1() {return d1;}
	public void setD1(String d1) {this.d1 = d1;}
	
	@JsonProperty("D2")
	private String d2;
	public String getD2() {return d2;}
	public void setD2(String d2) {this.d2 = d2;}
	
	@JsonProperty("D3")
	private String d3;
	public String getD3() {return d3;}
	public void setD3(String d3) {this.d3 = d3;}
	
	@JsonProperty("D4")
	private String d4;
	public String getD4() {return d4;}
	public void setD4(String d4) {this.d4 = d4;}
	
	@JsonProperty("Date1")
	private Date date1;
	public Date getDate1() {return date1;}
	public void setDate1(Date date1) {this.date1 = date1;}
	
	@JsonProperty("Date2")
	private Date date2;
	public Date getDate2() {return date2;}
	public void setDate2(Date date2) {this.date2 = date2;}
	
	@JsonProperty("Date3")
	private Date date3;
	public Date getDate3() {return date3;}
	public void setDate3(Date date3) {this.date3 = date3;}
	
	@JsonProperty("Date4")
	private Date date4;
	public Date getDate4() {return date4;}
	public void setDate4(Date date4) {this.date4 = date4;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}