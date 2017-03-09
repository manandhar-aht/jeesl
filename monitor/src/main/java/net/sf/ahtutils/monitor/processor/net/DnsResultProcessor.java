package net.sf.ahtutils.monitor.processor.net;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResultProcessor;

import org.jeesl.controller.monitoring.result.net.DnsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnsResultProcessor implements MonitoringResultProcessor<DnsResult>
{
	final static Logger logger = LoggerFactory.getLogger(DnsResultProcessor.class);

	private EntityManager em;
	private CompletionService<DnsResult> completionService;
	
    public DnsResultProcessor(EntityManager em, CompletionService<DnsResult> completionService)
    {
  	    this.em=em;
  	    this.completionService=completionService;
    }

	@Override
	public void run()
	{
		while(true)
        {
            try
            {
                Future<DnsResult> future = completionService.take();
                DnsResult result = future.get();
                
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
	
	@Override public void setCompletionService(CompletionService<DnsResult> completionService){this.completionService=completionService;}

}