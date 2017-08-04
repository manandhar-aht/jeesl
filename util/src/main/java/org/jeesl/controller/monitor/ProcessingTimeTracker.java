package org.jeesl.controller.monitor;

import java.util.ArrayList;
import java.util.List;
import org.jeesl.factory.txt.util.TxtPeriodFactory;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingTimeTracker
{
	final static Logger logger = LoggerFactory.getLogger(ProcessingTimeTracker.class);
	
	private long start;
	private long stop;
	private long previousEvent;
	private int counter;
	
	private final TxtPeriodFactory tfPeriod;
	
	public ProcessingTimeTracker() {this(false);}
	public ProcessingTimeTracker(boolean autoStart)
	{
		counter=0;
		start=0;
		previousEvent=0;
		stop=0;
		tfPeriod = new TxtPeriodFactory();
		tfPeriod.setUnits(TxtPeriodFactory.UNITS.minuteSecondMilli);
		if(autoStart){start();}
	}
	
	public void start() {start = System.currentTimeMillis();}
	public void stop() {stop = System.currentTimeMillis();}
	
	public void round()
	{
		counter++;
	}
	
	public int getCounter() {return counter;}
	
	public String toTotalTime()
	{
		if(stop==0){stop();}
		StringBuilder sb = new StringBuilder();
		sb.append(stop-start);
		sb.append(" ms");
		return sb.toString();
	}
	
	public String debugEvent(String event)
	{
		long now = System.currentTimeMillis();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		sb.append(tfPeriod.debugMillis(now-previousEvent));
		sb.append("] total [");
		sb.append(tfPeriod.debugMillis(now-start));
		sb.append("] for ");
		sb.append(event);
		previousEvent = now;
		return sb.toString();
	}
	
	public String toTotalPeriod()
	{
		if(stop==0){stop();}	
		Period period = new Period(new DateTime(start), new DateTime());
		return PeriodFormat.getDefault().print(period);
	}
	
	public String buildDefaultDebug(String prefix)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		sb.append(" ").append(counter).append(" times");
		sb.append(" in ").append(toTotalTime());
		if(counter>0)
		{
			long total = stop-start;
			sb.append(" avg: "+(total/counter));
		}
		return sb.toString();
	}
	
	private List<Long> ticksTime;
	private List<String> ticksMarker;
	private String ticker;
	
	public void startTicker(String ticker)
	{
		this.ticker=ticker;
		ticksTime = new ArrayList<Long>();
		ticksMarker = new ArrayList<String>();
		start = System.currentTimeMillis();
	}
	
	public void tick(String s)
	{
		ticksTime.add(System.currentTimeMillis());
		ticksMarker.add(s);
	}
	
	public void debugTicker()
	{
		logger.info("Debugging Ticker ("+ticksTime.size()+"): "+ticker);
		for(int i=0;i<ticksTime.size();i++)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("\t");
			sb.append(ticksMarker.get(i));
			
			long ms = ticksTime.get(i)-start;
			sb.append(": ").append(ms);
			logger.info(sb.toString());
		}
	}
}
