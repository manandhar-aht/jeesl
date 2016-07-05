package net.sf.ahtutils.jsf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullNumberBinder
{
	final static Logger logger = LoggerFactory.getLogger(NullNumberBinder.class);
	
	public NullNumberBinder()
	{
		a = "";
		b = "";
	}
	
	private String a; 
	public String getA() {return a;}
	public void setA(String a) {this.a = a;}
	public void integerToA(Integer i) {a = toString(i);}
	public Integer aToInteger() {return fromString(a);}
	
	private String b;
	public String getB() {return b;}
	public void setB(String b) {this.b = b;}
	public void integerToB(Integer i) {b = toString(i);}
	public Integer bToInteger() {return fromString(b);}
	
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
	
	private Integer fromString(String x)
	{
		Integer result = null;
		if(x==null || x.trim().length()==0){}
		else
		{
			result = new Integer(x);
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
}