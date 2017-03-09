package net.sf.ahtutils.monitor.task.util;

import java.util.Date;
import java.util.concurrent.Callable;

import org.jeesl.controller.monitoring.result.DebugResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugTask implements Callable<DebugResult>
{
	final static Logger logger = LoggerFactory.getLogger(DebugTask.class);
	
	private Date record;
	private String name;
	
	public DebugTask(String name)
	{
		this.name=name;
		record = new Date();
	}

	@Override
	public DebugResult call() throws Exception
	{
		DebugResult result = new DebugResult();
		result.setRecord(record);
	    result.setText(name);
		return result;
	}

}