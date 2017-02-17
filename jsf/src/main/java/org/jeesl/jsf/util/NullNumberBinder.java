package org.jeesl.jsf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullNumberBinder
{
	final static Logger logger = LoggerFactory.getLogger(NullNumberBinder.class);
	
	public NullNumberBinder()
	{
		a = "";
		b = "";
		c = "";
	}
	
	private String a; 
	public String getA() {return a;}
	public void setA(String a) {this.a = a;}
	public void integerToA(Integer value) {a = toString(value);}
	public void doubleToA(Double value) {a = toString(value);}
	public Integer aToInteger() {return integerFromString(a);}
	public Double aToDouble() {return doubleFromString(a);}
	
	private String b;
	public String getB() {return b;}
	public void setB(String b) {this.b = b;}
	public void integerToB(Integer value) {b = toString(value);}
	public void doubleToB(Double value) {b = toString(value);}
	public Integer bToInteger() {return integerFromString(b);}
	public Double bToDouble() {return doubleFromString(b);}
	
	private String c;
	public String getC() {return c;}
	public void setC(String c) {this.c = c;}
	public void integerToC(Integer value) {c = toString(value);}
	public void doubleToC(Double value) {c = toString(value);}
	public Integer cToInteger() {return integerFromString(c);}
	public Double cToDouble() {return doubleFromString(c);}
	
	private String d;
	public String getD() {return d;}
	public void setD(String d) {this.d = d;}
	public void integerToD(Integer value) {d = toString(value);}
	public void doubleToD(Double value) {b = toString(value);}
	public Integer dToInteger() {return integerFromString(d);}
	public Double dToDouble() {return doubleFromString(d);}
	
	private String toString(Integer i)
	{
		StringBuffer sb = new StringBuffer();
		if(i==null){sb.append("");}
		else{sb.append(i.intValue());}
		
		if(logger.isTraceEnabled())
		{
			StringBuffer sbd = new StringBuffer();
			sbd.append("Integer ");
			if(i==null){sbd.append("null");}
			else{sbd.append(i.intValue());}
			sbd.append(" returns ").append(sb.toString());
			logger.trace(sbd.toString());
		}
		
		return sb.toString();
	}
	
	private String toString(Double d)
	{
		StringBuffer sb = new StringBuffer();
		if(d==null){sb.append("");}
		else{sb.append(d.doubleValue());}
		
		if(logger.isTraceEnabled())
		{
			StringBuffer sbd = new StringBuffer();
			sbd.append("Double ");
			if(d==null){sbd.append("null");}
			else{sbd.append(d.doubleValue());}
			sbd.append(" returns ").append(sb.toString());
			logger.trace(sbd.toString());
		}
		
		return sb.toString();
	}
	
	private Integer integerFromString(String x)
	{
		Integer result = null;
		if(x==null || x.trim().length()==0){}
		else
		{
			result = Integer.valueOf(x);
		}
		
		if(logger.isTraceEnabled())
		{
			StringBuffer sb = new StringBuffer();
			sb.append("String: ").append(x);
			sb.append(" returns ");
			if(result==null){sb.append("null");}
			else{sb.append(result.intValue());}
			logger.trace(sb.toString());
		}
		
		return result;
	}
	
	private Double doubleFromString(String x)
	{
		Double result = null;
		if(x==null || x.trim().length()==0){}
		else
		{
			result = Double.valueOf(x);
		}
		
		if(logger.isTraceEnabled())
		{
			StringBuffer sb = new StringBuffer();
			sb.append("String: ").append(x);
			sb.append(" returns ");
			if(result==null){sb.append("null");}
			else{sb.append(result.doubleValue());}
			logger.trace(sb.toString());
		}
		
		return result;
	}
}