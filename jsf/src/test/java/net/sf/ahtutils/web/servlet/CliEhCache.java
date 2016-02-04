package net.sf.ahtutils.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.ahtutils.test.UtilsJsfTstBootstrap;

public class CliEhCache extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(CliEhCache.class);
		
	public CliEhCache()
	{
		logger.info("Starting");
	}
	
	public void build()
	{

	}
	
	public void use()
	{

	}
	
	public static void main(String[] args)
    {
		UtilsJsfTstBootstrap.init();		
			
		CliEhCache test = new CliEhCache();
		test.build();
		test.build();
    }
}