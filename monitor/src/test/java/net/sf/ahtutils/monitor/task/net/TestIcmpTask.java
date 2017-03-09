package net.sf.ahtutils.monitor.task.net;

import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.jeesl.controller.monitoring.result.net.IcmpResult;
import org.jeesl.controller.monitoring.result.net.IcmpResults;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestIcmpTask
{
	final static Logger logger = LoggerFactory.getLogger(TestIcmpTask.class);
	
	@Test
	public void test() throws Exception
	{
		IcmpTask task = new IcmpTask("localhost");
		IcmpResults results = task.call();
		for(IcmpResult result : results.getList())
		{
			Assert.assertEquals(IcmpResult.Code.REACHABLE, result.getCode());
		}
		
	}
	
	public static void main(String args[]) throws Exception
	{
		UtilsMonitorTestBootstrap.init();
		
		IcmpTask task = new IcmpTask("192.168.1.11");
		IcmpResults results = task.call();
		for(IcmpResult result : results.getList())
		{
			logger.info(result.toString());
		}
	}
}