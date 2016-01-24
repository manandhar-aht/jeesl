package net.sf.ahtutils.model.json.date;

import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.date.EntityWithQuarter;

public class JsonDateYQ implements EjbWithYear,EntityWithQuarter
{
	private int year;
	@Override public int getYear() {return year;}
	@Override public void setYear(int year) {this.year = year;}
	
	private String quarter;
	@Override public String getQuarter() {return quarter;}
	@Override public void setQuarter(String quarter) {this.quarter = quarter;}
}