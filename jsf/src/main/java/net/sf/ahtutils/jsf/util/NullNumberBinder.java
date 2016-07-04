package net.sf.ahtutils.jsf.util;

public class NullNumberBinder
{
	public NullNumberBinder()
	{
		a = "";
		b = "";
	}
	
	private String a; 
	public String getA() {return a;}
	public void setA(String a) {this.a = a;}
	public void integerToA(Integer i) {toString(i,a);}
	public Integer aToInteger() {return fromInteger(a);}
	
	
	private String b;
	public String getB() {return b;}
	public void setB(String b) {this.b = b;}
	public void integerToB(Integer i) {toString(i,b);}
	public Integer bToInteger() {return fromInteger(b);}
	
	private void toString(Integer i, String x)
	{
		if(i==null){x="";}
		else{x=""+i.intValue();}
	}
	
	private Integer fromInteger(String x)
	{
		if(x==null || x.trim().length()==0){return null;}
		else
		{
			return new Integer(x);
		}
	}
}