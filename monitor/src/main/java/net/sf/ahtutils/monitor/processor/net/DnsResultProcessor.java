package net.sf.ahtutils.monitor.processor.net;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import net.sf.ahtutils.interfaces.controller.MonitoringResultProcessor;
import net.sf.ahtutils.monitor.result.net.DnsResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnsResultProcessor implements MonitoringResultProcessor
{
	final static Logger logger = LoggerFactory.getLogger(DnsResultProcessor.class);

	private EntityManager em;
	private CompletionService<DnsResult> csDns;
	
    public DnsResultProcessor(EntityManager em, CompletionService<DnsResult> csDns)
    {
  	    this.em=em;
  	    this.csDns=csDns;
    }

	@Override
	public void run()
	{
		while(true)
        {
            try
            {
                Future<DnsResult> future = csDns.take();
                DnsResult result = future.get();
                
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}