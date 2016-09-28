package org.jeesl.model.json;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonProperty("Group4")
	private String g4;
	public String getG4() {return g4;}
	public void setG4(String g4) {this.g4 = g4;}
	
	@JsonProperty("Group5")
	private String g5;
	public String getG5() {return g5;}
	public void setG5(String g5) {this.g5 = g5;}
	
	@JsonProperty("Group6")
	private String g6;
	public String getG6() {return g6;}
	public void setG6(String g6) {this.g6 = g6;}
	
	@JsonProperty("Group7")
	private String g7;
	public String getG7() {return g7;}
	public void setG7(String g7) {this.g7 = g7;}
	
	@JsonProperty("Group8")
	private String g8;
	public String getG8() {return g8;}
	public void setG8(String g8) {this.g8 = g8;}
	
	@JsonProperty("Group9")
	private String g9;
	public String getG9() {return g9;}
	public void setG9(String g9) {this.g9 = g9;}
	
	@JsonProperty("Data")
	private Map<Integer,Double> data;
	public Map<Integer, Double> getData() {return data;}
	public void setData(Map<Integer, Double> data) {this.data = data;}

	@JsonProperty("Data1")
	private double d1;
	public double getD1() {return d1;}
	public void setD1(double d1) {this.d1 = d1;}
	
	@JsonProperty("Data2")
	private double d2;
	public double getD2() {return d2;}
	public void setD2(double d2) {this.d2 = d2;}
	
	@JsonProperty("Data3")
	private double d3;
	public double getD3() {return d3;}
	public void setD3(double d3) {this.d3 = d3;}

	@JsonProperty("Data4")
	private double d4;
	public double getD4() {return d4;}
	public void setD4(double d4) {this.d4 = d4;}
	
	@JsonProperty("Data5")
	private double d5;
	public double getD5() {return d5;}
	public void setD5(double d5) {this.d5 = d5;}
	
	@JsonProperty("Data6")
	private double d6;
	public double getD6() {return d6;}
	public void setD6(double d6) {this.d6 = d6;}
	
	@JsonProperty("Data7")
	private double d7;
	public double getD7() {return d7;}
	public void setD7(double d7) {this.d7 = d7;}
	
	@JsonProperty("Data8")
	private double d8;
	public double getD8() {return d8;}
	public void setD8(double d8) {this.d8 = d8;}
	
	@JsonProperty("Data9")
	private double d9;
	public double getD9() {return d9;}
	public void setD9(double d9) {this.d9 = d9;}
	
	
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
		
	
	@JsonProperty("Counter1")
	private int c1;
	public int getC1() {return c1;}
	public void setC1(int c1) {this.c1 = c1;}
	
	@JsonProperty("Counter2")
	private int c2;
	public int getC2() {return c2;}
	public void setC2(int c2) {this.c2 = c2;}
	
	@JsonProperty("Counter3")
	private int c3;
	public int getC3() {return c3;}
	public void setC3(int c3) {this.c3 = c3;}
	
	@JsonProperty("Counter4")
	private int c4;
	public int getC4() {return c4;}
	public void setC4(int c4) {this.c4 = c4;}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}