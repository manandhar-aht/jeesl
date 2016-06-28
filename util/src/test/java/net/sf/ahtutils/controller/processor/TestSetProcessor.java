package net.sf.ahtutils.controller.processor;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsTest;

public class TestSetProcessor extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSetProcessor.class);
	
	private List<String> a,b,c,d;
	
	@Before public void init()
	{
		a = Arrays.asList("a b c d"      .split(" "));
		b = Arrays.asList(      "d e f g".split(" "));
	}
	
	@Test public void pre()
    {	
    	Assert.assertEquals(4, a.size());
    	Assert.assertEquals(4, b.size());
    }
 
    @Test public void and()
    {	
    	List<String> result = SetProcessor.and(a,b);
    	Assert.assertEquals(1, result.size());
    	Assert.assertEquals("d", result.get(0));
    }
    
    @Test
    public void or()
    {	
    	List<String> result = SetProcessor.or(a,b);
    	Assert.assertEquals(7, result.size());
    }
}