package net.sf.ahtutils.monitor.factory;

import org.jeesl.api.facade.module.JeeslMonitoringFacade;

import net.sf.ahtutils.monitor.util.DbCleaner;

public class AbstractTransmissionFactory
{
	protected JeeslMonitoringFacade fUm;
	protected DbCleaner dbCleaner;
	
	public  AbstractTransmissionFactory(JeeslMonitoringFacade fUm, DbCleaner dbCleaner)
	{
		this.fUm=fUm;
		this.dbCleaner=dbCleaner;
	}
	
	public AbstractTransmissionFactory(JeeslMonitoringFacade fUm)
	{
		this.fUm=fUm;
	}
}
