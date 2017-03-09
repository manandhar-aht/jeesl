package net.sf.ahtutils.monitor.factory.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;

import org.jeesl.controller.monitoring.result.DebugResult;

import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringTaskFactory;
import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResultProcessor;
import net.sf.ahtutils.monitor.task.util.DebugTask;

public class DebugMonitoringTaskFactory implements MonitoringTaskFactory<DebugResult>
{
	private CompletionService<DebugResult> completionService;
	private MonitoringResultProcessor<DebugResult> resultProcessor;
	
	private String name;
	
	public DebugMonitoringTaskFactory(String name)
	{
		this.name = name;
	}
	
	@Override public CompletionService<DebugResult> getCompletionService(){return completionService;}
	@Override public void setCompletionService(CompletionService<DebugResult> completionService){this.completionService=completionService;}

	@Override public MonitoringResultProcessor<DebugResult> getResultProcessor(){return resultProcessor;}
	@Override public void setResultProcessor(MonitoringResultProcessor<DebugResult> resultProcessor){this.resultProcessor=resultProcessor;}

	@Override
	public Callable<DebugResult> buildTask()
	{
		return new DebugTask(name);
	}
	
	
}
