package net.sf.ahtutils.factory.txt.sync;

import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.ahtutils.test.AhtUtilsTestBootstrap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtDataUpdateFactory extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtDataUpdateFactory.class);
	
	private DataUpdateTracker dut;
	
	@Before
	public void init()
	{
		dut = new DataUpdateTracker();
		dut.setType(XmlTypeFactory.build(TestTxtDataUpdateFactory.class.getName(),"Now Testing:"));
	}
 
    @Test
    public void server()
    {	
    	String expected = "Now Testing: TestTxtDataUpdateFactory";
    	String actual = TxtDataUpdateFactory.debug(dut.getUpdate());
    	logger.debug(actual);
    	Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void client()
    {	
    	dut.success();
    	String expected = "Now Testing: TestTxtDataUpdateFactory [success] 1/1 (skipped:0)";
    	String actual = TxtDataUpdateFactory.debug(dut.toDataUpdate());
    	logger.debug(actual);
    	Assert.assertEquals(expected, actual);
    }
    
	public static void main (String[] args) throws Exception
	{
		AhtUtilsTestBootstrap.init();
		
		TestTxtDataUpdateFactory test = new TestTxtDataUpdateFactory();
		test.init();
		
		test.server();
		test.client();
	}
}