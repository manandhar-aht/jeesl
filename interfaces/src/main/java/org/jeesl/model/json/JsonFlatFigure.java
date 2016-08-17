package org.jeesl.model.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class JsonFlatFigure implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("Group1")
	private String g1;
	public String getG1() {return g1;}
	public void setG1(String g1) {this.g1 = g1;}

	@JsonProperty("Group2")
	private String g2;
	public String getG2() {return g2;}
	public void setG2(String g2) {this.g2 = g2;}
	
	@JsonProperty("Group3")
	private String g3;
	public String getG3() {return g3;}
	public void setG3(String g3) {this.g3 = g3;}
	
	@JsonProperty("Data1")
	private String d1;
	public String getD1() {return d1;}
	public void setD1(String d1) {this.d1 = d1;}
	
	@JsonProperty("Data2")
	private String d2;
	public String getD2() {return d2;}
	public void setD2(String d2) {this.d2 = d2;}
	
	@JsonProperty("Data3")
	private String d3;
	public String getD3() {return d3;}
	public void setD3(String d3) {this.d3 = d3;}

	@JsonProperty("Data4")
	private String d4;
	public String getD4() {return d4;}
	public void setD4(String d4) {this.d4 = d4;}
	
	@JsonProperty("Data5")
	private String d5;
	public String getD5() {return d5;}
	public void setD5(String d5) {this.d5 = d5;}
	
	@JsonProperty("Data6")
	private String d6;
	public String getD6() {return d6;}
	public void setD6(String d6) {this.d6 = d6;}
	
	@JsonProperty("Data7")
	private String d7;
	public String getD7() {return d7;}
	public void setD7(String d7) {this.d7 = d7;}
	
	@JsonProperty("Data8")
	private String d8;
	public String getD8() {return d8;}
	public void setD8(String d8) {this.d8 = d8;}
	
	@JsonProperty("Data9")
	private String d9;
	public String getD9() {return d9;}
	public void setD9(String d9) {this.d9 = d9;}
	
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
	
	@JsonProperty("Finance1")
	private double f1;
	public double getF1() {return f1;}
	public void setF1(double f1) {this.f1 = f1;}
	
	@JsonProperty("Finance2")
	private double f2;
	public double getF2() {return f2;}
	public void setF2(double f2) {this.f2 = f2;}
	
	@JsonProperty("Counter1")
	private int c1;
	public int getC1() {return c1;}
	public void setC1(int c1) {this.c1 = c1;}
	
	@JsonProperty("Counter2")
	private int c2;
	public int getC2() {return c2;}
	public void setC2(int c2) {this.c2 = c2;}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}