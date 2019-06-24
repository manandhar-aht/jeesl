package org.jeesl.model.json;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonFlatFigure implements Serializable
{
	public static final long serialVersionUID=1;

	private EjbWithId ejb1;
	public EjbWithId getEjb1() {return ejb1;}
	public void setEjb1(EjbWithId ejb1) {this.ejb1 = ejb1;}
	
	private EjbWithId ejb2;
	public EjbWithId getEjb2() {return ejb2;}
	public void setEjb2(EjbWithId ejb2) {this.ejb2 = ejb2;}

	@JsonProperty("S1")
	private String s1;
	public String getS1() {return s1;}
	public void setS1(String s1) {this.s1 = s1;}
	
	@JsonProperty("S2")
	private String s2;
	public String getS2() {return s2;}
	public void setS2(String s2) {this.s2 = s2;}
	
	@JsonProperty("S3")
	private String s3;
	public String getS3() {return s3;}
	public void setS3(String s3) {this.s3 = s3;}
	
	@JsonProperty("S4")
	private String s4;
	public String getS4() {return s4;}
	public void setS4(String s4) {this.s4 = s4;}
	
	@JsonProperty("S5")
	private String s5;
	public String getS5() {return s5;}
	public void setS5(String s5) {this.s5 = s5;}
	
	@JsonProperty("S6")
	private String s6;
	public String getS6() {return s6;}
	public void setS6(String s6) {this.s6 = s6;}
	
	@JsonProperty("S7")
	private String s7;
	public String getS7() {return s7;}
	public void setS7(String s7) {this.s7 = s7;}
	
	
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
	
	@JsonProperty("Group10")
	private String g10;
	public String getG10() {return g10;}
	public void setG10(String g10) {this.g10 = g10;}
	
	@JsonProperty("Group11")
	private String g11;
	public String getG11() {return g11;}
	public void setG11(String g11) {this.g11 = g11;}
	
	
	@JsonProperty("Data")
	private Map<Integer,Double> data;
	public Map<Integer, Double> getData() {return data;}
	public void setData(Map<Integer, Double> data) {this.data = data;}
	
	@JsonProperty("List1")
	private List<Double> list1;
	public List<Double> getList1() {return list1;}
	public void setList1(List<Double> list1) {this.list1 = list1;}

	@JsonProperty("List2")
	private List<Double> list2;
	public List<Double> getList2() {return list2;}
	public void setList2(List<Double> list2) {this.list2 = list2;}

	@JsonProperty("List3")
	private List<Double> list3;
	public List<Double> getList3() {return list3;}
	public void setList3(List<Double> list3) {this.list3 = list3;}
	
	@JsonProperty("List4")
	private List<Double> list4;
	public List<Double> getList4() {return list4;}
	public void setList4(List<Double> list4) {this.list4 = list4;}
	
	@JsonProperty("Ids1")
	private List<Long> ids1;
	public List<Long> getIds1() {return ids1;}
	public void setIds1(List<Long> ids1) {this.ids1 = ids1;}
	
	@JsonProperty("Data1")
	private Double d1;
	public Double getD1() {return d1;}
	public void setD1(Double d1) {this.d1 = d1;}
	
	@JsonProperty("Data2")
	private Double d2;
	public Double getD2() {return d2;}
	public void setD2(Double d2) {this.d2 = d2;}
	
	@JsonProperty("Data3")
	private Double d3;
	public Double getD3() {return d3;}
	public void setD3(Double d3) {this.d3 = d3;}

	@JsonProperty("Data4")
	private Double d4;
	public Double getD4() {return d4;}
	public void setD4(Double d4) {this.d4 = d4;}
	
	@JsonProperty("Data5")
	private Double d5;
	public Double getD5() {return d5;}
	public void setD5(Double d5) {this.d5 = d5;}
	
	@JsonProperty("Data6")
	private Double d6;
	public Double getD6() {return d6;}
	public void setD6(Double d6) {this.d6 = d6;}
	
	@JsonProperty("Data7")
	private Double d7;
	public Double getD7() {return d7;}
	public void setD7(Double d7) {this.d7 = d7;}
	
	@JsonProperty("Data8")
	private Double d8;
	public Double getD8() {return d8;}
	public void setD8(Double d8) {this.d8 = d8;}
	
	@JsonProperty("Data9")
	private Double d9;
	public Double getD9() {return d9;}
	public void setD9(Double d9) {this.d9 = d9;}
	
	@JsonProperty("Data10")
	private Double d10;
	public Double getD10() {return d10;}
	public void setD10(Double d10) {this.d10 = d10;}
	
	@JsonProperty("Data11")
	private Double d11;
	public Double getD11() {return d11;}
	public void setD11(Double d11) {this.d11 = d11;}
	
	@JsonProperty("Data12")
	private Double d12;
	public Double getD12() {return d12;}
	public void setD12(Double d12) {this.d12 = d12;}
	
	@JsonProperty("Data13")
	private Double d13;
	public Double getD13() {return d13;}
	public void setD13(Double d13) {this.d13 = d13;}
	
	@JsonProperty("Data14")
	private Double d14;
	public Double getD14() {return d14;}
	public void setD14(Double d14) {this.d14 = d14;}
	
	@JsonProperty("Data15")
	private Double d15;
	public Double getD15() {return d15;}
	public void setD15(Double d15) {this.d15 = d15;}
	
	@JsonProperty("Data16")
	private Double d16;
	public Double getD16() {return d16;}
	public void setD16(Double d16) {this.d16 = d16;}
	
	@JsonProperty("Data17")
	private Double d17;
	public Double getD17() {return d17;}
	public void setD17(Double d17) {this.d17 = d17;}
	
	@JsonProperty("Data18")
	private Double d18;
	public Double getD18() {return d18;}
	public void setD18(Double d18) {this.d18 = d18;}
	
	@JsonProperty("Data19")
	private Double d19;
	public Double getD19() {return d19;}
	public void setD19(Double d19) {this.d19 = d19;}
	
	@JsonProperty("Data20")
	private Double d20;
	public Double getD20() {return d20;}
	public void setD20(Double d20) {this.d20 = d20;}
	
	
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
		
	
	@JsonProperty("B1")
	private Boolean b1;
	public Boolean getB1() {return b1;}
	public void setB1(Boolean b1) {this.b1 = b1;}
	
	@JsonProperty("B2")
	private Boolean b2;
	public Boolean getB2() {return b2;}
	public void setB2(Boolean b2) {this.b2 = b2;}
	
	
	@JsonProperty("I1")
	private Integer i1;
	public Integer getI1() {return i1;}
	public void setI1(Integer i1) {this.i1 = i1;}
	
	
	@JsonProperty("Counter1")
	private Integer c1;
	public Integer getC1() {return c1;}
	public void setC1(Integer c1) {this.c1 = c1;}
	
	@JsonProperty("Counter2")
	private Integer c2;
	public Integer getC2() {return c2;}
	public void setC2(Integer c2) {this.c2 = c2;}
	
	@JsonProperty("Counter3")
	private Integer c3;
	public Integer getC3() {return c3;}
	public void setC3(Integer c3) {this.c3 = c3;}
	
	@JsonProperty("Counter4")
	private Integer c4;
	public Integer getC4() {return c4;}
	public void setC4(Integer c4) {this.c4 = c4;}
	
	@JsonProperty("Counter5")
	private Integer c5;
	public Integer getC5() {return c5;}
	public void setC5(Integer c5) {this.c5 = c5;}
	
	@JsonProperty("Counter6")
	private Integer c6;
	public Integer getC6() {return c6;}
	public void setC6(Integer c6) {this.c6 = c6;}
	
	@JsonProperty("Counter7")
	private Integer c7;
	public Integer getC7() {return c7;}
	public void setC7(Integer c7) {this.c7 = c7;}
	
	@JsonProperty("Counter8")
	private Integer c8;
	public Integer getC8() {return c8;}
	public void setC8(Integer c8) {this.c8 = c8;}
	
	@JsonProperty("Counter9")
	private Integer c9;
	public Integer getC9() {return c9;}
	public void setC9(Integer c9) {this.c9 = c9;}
	
	@JsonProperty("Counter10")
	private Integer c10;
	public Integer getC10() {return c10;}
	public void setC10(Integer c10) {this.c10 = c10;}
	
	@JsonProperty("Long1")
	private Long l1;
	public Long getL1() {return l1;}
	public void setL1(Long l1) {this.l1 = l1;}
	
	@JsonProperty("Long2")
	private Long l2;
	public Long getL2() {return l2;}
	public void setL2(Long l2) {this.l2 = l2;}
	
	@JsonProperty("Long3")
	private Long l3;
	public Long getL3() {return l3;}
	public void setL3(Long l3) {this.l3 = l3;}
	
	@JsonProperty("Long4")
	private Long l4;
	public Long getL4() {return l4;}
	public void setL4(Long l4) {this.l4 = l4;}
}