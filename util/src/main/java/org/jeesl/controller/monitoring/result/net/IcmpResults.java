package org.jeesl.controller.monitoring.result.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResult;

public class IcmpResults implements Serializable,MonitoringResult
{
	public static final long serialVersionUID=1;
	
	private List<IcmpResult> list;
	
	public IcmpResults()
	{
		list = new ArrayList<IcmpResult>();
	}
	
	public void add(IcmpResult icmp)
	{
		list.add(icmp);
	}
	
	public void add(List<IcmpResult> icmps)
	{
		list.addAll(icmps);
	}
	
	
	public List<IcmpResult> getList() {return list;}
}