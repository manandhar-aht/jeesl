package net.sf.ahtutils.monitor.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManagerFactory;

import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringTaskFactory;
import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResult;
import net.sf.ahtutils.interfaces.controller.monitoring.MonitoringResultProcessor;
import net.sf.ahtutils.monitor.processor.net.DnsResultProcessor;
import net.sf.ahtutils.monitor.processor.net.IcmpResultProcessor;

import org.jeesl.controller.monitoring.result.net.DnsResult;
import org.jeesl.controller.monitoring.result.net.IcmpResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringWorker <R extends MonitoringResult> implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTaskBuilder.class);
	
	private EntityManagerFactory emf;
	private List<Thread> threads;
	
	private ExecutorService taskExecutor;
	private MonitoringScheduler<R> scheduler;
	
	public MonitoringWorker(){this(null);}
	
	public MonitoringWorker(EntityManagerFactory emf)
	{
    	this.emf = emf;
    
    	taskExecutor = Executors.newFixedThreadPool(100);
        threads = new ArrayList<Thread>();
        
        scheduler = new MonitoringScheduler<R>();
        threads.add(new Thread(scheduler));
	}

	@Override
	public void run()
	{
		logger.info("Starting "+this.getClass().getSimpleName());
		for(Thread t : threads)
		{
			t.start();
		}
		boolean active = true;
		while(active)
		{
			try {Thread.sleep(1000*10);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	@Deprecated
	public void initHardWiredTasks()
	{
		logger.debug("Creating initHardWiredTasks"+MonitoringScheduler.class.getSimpleName());
		
        CompletionService<DnsResult> csDns = new ExecutorCompletionService<DnsResult>(taskExecutor);
        scheduler.setCsDns(csDns);
        threads.add(new Thread(new DnsResultProcessor(emf.createEntityManager(),csDns)));
        
        CompletionService<IcmpResults> csIcmp = new ExecutorCompletionService<IcmpResults>(taskExecutor);
        scheduler.setCsIcmp(csIcmp);
		threads.add(new Thread(new IcmpResultProcessor(emf.createEntityManager(),csIcmp)));
	}
	
	public void addTaskFactory(MonitoringTaskFactory<R> taskFactory)
	{
		logger.info("Adding TaskBundle");
		
		CompletionService<R> completionService = new ExecutorCompletionService<R>(taskExecutor);
		taskFactory.setCompletionService(completionService);
		
		MonitoringResultProcessor<R> resultProcessor = taskFactory.getResultProcessor();
		resultProcessor.setCompletionService(completionService);
		
		threads.add(new Thread(resultProcessor));
		scheduler.addTaskFactory(taskFactory);
	}
}