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
	private boolean enforceStartOfDay;
	
	private Date date1; public Date getDate1() {return date1;} 
	private Date date2; public Date getDate2() {return date2;}

	public SbDateHandler()
	{
		enforceStartOfDay = false;
	}
	
	public SbDateHandler(SbDateIntervalSelection bean)
	{
		this.bean=bean;
		enforceStartOfDay = true;
	}
	
	public void initMonthsToNow(int months)
	{
		DateTime dt = new DateTime();
		setDate1(dt.minusMonths(months).toDate());
		setDate2(dt.toDate());
	}
	
	public void initMonths(int from, int to)
	{
		DateTime dt = new DateTime();
		setDate1(dt.minusMonths(from).toDate());
		setDate2(dt.plusMonths(to).toDate());
	}
	
	public void initWeeksToNow(int weeks)
	{
		DateTime dt = new DateTime();
		setDate1(dt.minusWeeks(weeks).toDate());
		setDate2(dt.toDate());
	}
	
	public void initDaysToNow(int days)
	{
		DateTime dt = new DateTime();
		setDate1(dt.minusDays(days).toDate());
		setDate2(dt.toDate());
	}
	
	public void initWeeks(int minus, int plus)
	{
		DateTime dt = new DateTime();
		setDate1(dt.minusWeeks(minus).toDate());
		setDate2(dt.plusWeeks(plus).toDate());
	}
	
	public void setDate1(Date date1)
	{
		if(enforceStartOfDay)
		{
			DateTime dt = new DateTime(date1);
			this.date1 = dt.withTimeAtStartOfDay().toDate();
		}
		else {this.date1 = date1;}
	}
	
	public void setDate2(Date date2)
	{
		if(enforceStartOfDay)
		{
			DateTime dt = new DateTime(date2);
			this.date2 = dt.withTimeAtStartOfDay().toDate();
		}
		else {this.date2 = date2;}
	}
	
	public void dateChanged()
	{
		logger.info("changed");
		if(bean!=null){bean.callbackDateChanged();}
	}
}