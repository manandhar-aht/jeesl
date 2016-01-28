package net.sf.ahtutils.test.model.ejb.status.cli;

import java.util.Random;

import net.sf.ahtutils.model.ejb.status.Status;
import net.sf.ahtutils.test.AhtUtilsEjbTestBootstrap;
import net.sf.ahtutils.test.model.ejb.status.TestStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstStatus
{
	final static Logger logger = LoggerFactory.getLogger(TstStatus.class);
	
	private Random rnd;
	
	public TstStatus()
	{
		rnd = new Random();
	}
	
	public void create()
	{
		Status status = TestStatus.create(rnd, "code");
		logger.debug(status.toString());
	}
	
	public static void main(String[] args)
    {
		AhtUtilsEjbTestBootstrap.init();

		TstStatus test = new TstStatus();
		test.create();
    }
}