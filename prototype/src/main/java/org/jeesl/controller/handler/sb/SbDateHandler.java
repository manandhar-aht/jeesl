package org.jeesl.controller.handler.sb;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SbDateHandler implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SbDateHandler.class);
	private static final long serialVersionUID = 1L;

	private SbDateIntervalSelection bean;
	
	private Date date1; public Date getDate1() {return date1;} public void setDate1(Date date1) {this.date1 = date1;}
	private Date date2; public Date getDate2() {return date2;} public void setDate2(Date date2) {this.date2 = date2;}

	public SbDateHandler()
	{
		
	}
	
	public SbDateHandler(SbDateIntervalSelection bean)
	{
		this.bean=bean;
	}
	
	public void initMonthsToNow(int months)
	{
		DateTime dt = new DateTime();
		date1 = dt.minusMonths(months).toDate();
		date2 = dt.toDate();
	}
	
	public void initWeeks(int minus, int plus)
	{
		DateTime dt = new DateTime();
		date1 = dt.minusWeeks(minus).toDate();
		date2 = dt.plusWeeks(plus).toDate();
	}
	
	public void dateChanged()
	{
		logger.info("changed");
		if(bean!=null){bean.callbackDateChanged();}
	}
}