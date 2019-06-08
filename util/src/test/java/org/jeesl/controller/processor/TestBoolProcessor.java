package org.jeesl.controller.processor;

import java.util.Arrays;
import java.util.List;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBoolProcessor extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestBoolProcessor.class);
	
	private List<Boolean> a;

	@Before public void init()
	{		
		Boolean tmp [] = {true, false, true, true, true, false, false, true}; //AND - false , OR - true
		a = Arrays.asList(tmp);
	}
	
	@Test public void pre()
    {	
		Assert.assertEquals(8, a.size());
    }
 

    @Test public void and()
    {
		Boolean actualA = BooleanProcessor.query("true AND false",a);
		Assert.assertFalse(actualA);
    }
    
    @Test public void or()
    {
		Boolean actualA = BooleanProcessor.query("true OR false",a);
		Assert.assertTrue(actualA);
    }
}