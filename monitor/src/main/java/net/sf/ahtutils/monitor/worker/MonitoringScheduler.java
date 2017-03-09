package net.sf.ahtutils.monitor.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CompletionService;

import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResult;
import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringTaskFactory;

import org.jeesl.controller.monitoring.result.DebugResult;
import org.jeesl.controller.monitoring.result.net.DnsResult;
import org.jeesl.controller.monitoring.result.net.IcmpResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringScheduler <R extends MonitoringResult> implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringScheduler.class);

	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResults> csIcmp;
	private CompletionService<DebugResult> csDebug;
	
	private List<MonitoringTaskFactory<R>> taskFactories;
	

	public MonitoringScheduler()
    {
		taskFactories = new ArrayList<MonitoringTaskFactory<R>>();
    }

	@Override
	public void run()
	{
        MonitoringTaskBuilder<R> monitoringTask  = new MonitoringTaskBuilder<R>();
    	monitoringTask.setCsDns(csDns);
    	monitoringTask.setCsIcmp(csIcmp);
    	monitoringTask.setCsDebug(csDebug);
    	
    	monitoringTask.setListMonitoringBundles(taskFactories);

 	    Timer timer = new Timer();
 	    logger.info("Starting timer");
 	    timer.scheduleAtFixedRate(monitoringTask, new Date(), 1000);
 	    logger.info("Time scheduled");
	}
	
	public void shutdown()
	{
		
	}
	
	public void addTaskFactory(MonitoringTaskFactory<R> taskFactory)
	{
		logger.trace("Adding TaskBundle");
		taskFactories.add(taskFactory);
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResults> csIcmp) {this.csIcmp = csIcmp;}
	public void setCsDebug(CompletionService<DebugResult> csDebug) {this.csDebug = csDebug;}
}