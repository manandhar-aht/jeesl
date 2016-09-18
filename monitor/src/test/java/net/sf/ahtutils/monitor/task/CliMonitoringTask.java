package net.sf.ahtutils.monitor.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.monitor.worker.MonitoringTaskBuilder;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

public class CliMonitoringTask
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringTask.class);
	
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
	    TimerTask fetchMail  = new MonitoringTaskBuilder();

	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(fetchMail, new Date(), 1000);
	  }
}