package org.jeesl.controller.processor;

import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.system.status.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestBoolProcessor extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestBoolProcessor.class);
	
	private static EjbStatusFactory<Status,Lang,Description> factory;
	
	private List<Boolean> a;

	
	@BeforeClass public static void initClass()
	{
		factory = EjbStatusFactory.createFactory(Status.class,Lang.class,Description.class);
	}
	
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