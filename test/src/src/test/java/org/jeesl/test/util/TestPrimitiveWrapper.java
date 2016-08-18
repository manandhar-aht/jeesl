package org.jeesl.test.util;

import org.jeesl.test.AbstractJeeslTestTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPrimitiveWrapper extends AbstractJeeslTestTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPrimitiveWrapper.class);
		
	@Test public void doubleEquals()
	{
		Double a = 0.0d;
		Double b = 0.0d;
		Assert.assertFalse(a.equals(null));
		Assert.assertTrue(a.equals(b));
	}
	
	@Test(expected=NullPointerException.class)
	public void doubleEqualsNpw()
	{
		Double a = 0.0d;
		Double c = null;
		Assert.assertTrue(c.equals(a));
	}
}