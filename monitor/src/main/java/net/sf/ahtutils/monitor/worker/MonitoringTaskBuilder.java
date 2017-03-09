package net.sf.ahtutils.monitor.worker;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CompletionService;

import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringTaskFactory;
import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResult;
import net.sf.ahtutils.monitor.task.net.DnsTask;
import net.sf.ahtutils.monitor.task.net.IcmpTask;
import net.sf.ahtutils.monitor.task.util.DebugTask;

import org.jeesl.controller.monitoring.result.DebugResult;
import org.jeesl.controller.monitoring.result.net.DnsResult;
import org.jeesl.controller.monitoring.result.net.IcmpResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringTaskBuilder <R extends MonitoringResult> extends TimerTask 
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTaskBuilder.class);
	
	private long counter;
	
	private List<MonitoringTaskFactory<R>> listMonitoringBundles;
	
	public List<MonitoringTaskFactory<R>> getListTaskBundles() {return listMonitoringBundles;}
	
	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResults> csIcmp;
	private CompletionService<DebugResult> csDebug;

	public MonitoringTaskBuilder()
	{
		counter=0;
	}
	
	public void run()
	{
		if(csDns!=null){buildDnsTask();}
		if(csIcmp!=null){buildIcmpTask();}
		if(csDebug!=null){buildDebug();}
		
		if(listMonitoringBundles==null || listMonitoringBundles.size()==0)
		{
			logger.warn("No "+MonitoringTaskFactory.class.getSimpleName()+" available");
		}
		else
		{
			for(MonitoringTaskFactory<R> taskBundle : listMonitoringBundles)
			{
				buildTask(taskBundle);
			}
		}
		
		counter++;
	}
	
	private void buildTask(MonitoringTaskFactory<R> taskBundle)
	{
		if(logger.isTraceEnabled()){logger.trace("Building "+MonitoringTaskFactory.class.getSimpleName()+": "+taskBundle.getClass().getSimpleName());}
		taskBundle.getCompletionService().submit(taskBundle.buildTask());
	}
	
	private void buildDnsTask()
	{
		DnsTask task = new DnsTask("8.8.8.8","test"+counter+".google.com");
		csDns.submit(task);
	}
	
	private void buildIcmpTask()
	{
		IcmpTask task = new IcmpTask("www.google.com");
		if(counter%60==0)
		{
			csIcmp.submit(task);
		}
	}
	
	private void buildDebug()
	{
		DebugTask task = new DebugTask("test");
		csDebug.submit(task);
	}
	
	public void setListMonitoringBundles(List<MonitoringTaskFactory<R>> listTaskBundles) {this.listMonitoringBundles = listTaskBundles;}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResults> csIcmp) {this.csIcmp = csIcmp;}
	public void setCsDebug(CompletionService<DebugResult> csDebug) {this.csDebug = csDebug;}
}
