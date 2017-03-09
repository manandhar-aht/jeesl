package net.sf.ahtutils.monitor.processor.net;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.jeesl.controller.monitoring.result.net.IcmpResult;
import org.jeesl.controller.monitoring.result.net.IcmpResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpResultProcessor implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(IcmpResultProcessor.class);

	private EntityManager em;
	private CompletionService<IcmpResults> csIcmp;
	
    public IcmpResultProcessor(EntityManager em, CompletionService<IcmpResults> csIcmp)
    {
  	    this.em=em;
  	    this.csIcmp=csIcmp;
    }

	@Override
	public void run()
	{
		while(true)
        {
            try
            {
                Future<IcmpResults> future = csIcmp.take();
                IcmpResults results = future.get();
                
                em.getTransaction().begin();
                for(IcmpResult result : results.getList())
                {
                	em.persist(result);
                }
                em.getTransaction().commit();
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}