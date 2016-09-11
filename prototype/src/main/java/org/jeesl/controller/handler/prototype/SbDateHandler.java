package org.jeesl.controller.handler.prototype;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SbDateHandler implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SbDateHandler.class);
	private static final long serialVersionUID = 1L;

	private Date date1; public Date getDate1() {return date1;} public void setDate1(Date date1) {this.date1 = date1;}
	private Date date2; public Date getDate2() {return date2;} public void setDate2(Date date2) {this.date2 = date2;}

	public SbDateHandler()
	{
		
	}
	
	public void initMonthsToNow(int months)
	{
		DateTime dt = new DateTime();
		date2 = dt.toDate();
		date1 = dt.minusMonths(months).toDate();
	}
}