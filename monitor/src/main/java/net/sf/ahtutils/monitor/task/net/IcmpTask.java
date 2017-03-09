package net.sf.ahtutils.monitor.task.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.sf.exlp.core.handler.EhList;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.shell.spawn.ping.IcmpPing;
import net.sf.exlp.shell.spawn.ping.PingEvent;

import org.jeesl.controller.monitoring.result.net.IcmpResult;
import org.jeesl.controller.monitoring.result.net.IcmpResults;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpTask implements Callable<IcmpResults>
{
	final static Logger logger = LoggerFactory.getLogger(IcmpTask.class);
	
	private String host;
	
	public IcmpTask(String host)
	{
		this.host=host;
	}

	@Override
	public IcmpResults call()
	{
		MutableDateTime mdt = new MutableDateTime();
		
		IcmpResults results = new IcmpResults();
		
		InetAddress iaHost;
		try
		{
			iaHost = getAddress();
			List<LogEvent> list = new ArrayList<LogEvent>();
			LogEventHandler leh = new EhList(list);
			IcmpPing ping = new IcmpPing(iaHost.getHostAddress(),5);
			ping.ping(leh);
			
			for(LogEvent le : list)
			{
				logger.info("Recogniting pingevent");
				PingEvent pe = (PingEvent)le;
				IcmpResult result = new IcmpResult();
				result.setDuration((new Double(pe.getTime())).longValue());
				result.setCode(IcmpResult.Code.REACHABLE);
				result.setRecord(mdt.toDate());
				results.add(result);
				mdt.addSeconds(1);
			}
		}
		catch (UnknownHostException e) {e.printStackTrace();}	    
		return results;
	}
	
	private InetAddress getAddress() throws UnknownHostException
	{
		InetAddress iaHost;
		iaHost = InetAddress.getByName(host);
		return iaHost;
	}
}