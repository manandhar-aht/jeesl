package org.jeesl.test.util;

import org.jeesl.test.AbstractJeeslTestTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCallByReference extends AbstractJeeslTestTest
{
	final static Logger logger = LoggerFactory.getLogger(TestCallByReference.class);
		
	@Test public void primitive()
	{
		Integer a = 5;
		int b = 5;
		Assert.assertEquals(a.intValue(), b);
		
		methodCall(a);
		methodCall(b);
		
		Assert.assertEquals(5,a.intValue());
		Assert.assertEquals(5,b);
	}
	
	private void methodCall(Integer i)
	{
		i = 4;
	}
	
	@Test public void objectReference()
	{
		InnerClass a = new InnerClass();
		a.setIndex(1);
		Assert.assertEquals(1,a.getIndex());
		
		InnerClass b=a;
		b.setIndex(2);
		Assert.assertEquals(2,b.getIndex());
		Assert.assertEquals(2,a.getIndex());
	}
	
	@Test public void objectCall()
	{
		InnerClass a = new InnerClass();
		a.setIndex(1);
		Assert.assertEquals(1,a.getIndex());
		
		methodCall(a);
		Assert.assertEquals(2,a.getIndex());
	}
	
	private void methodCall(InnerClass i)
	{
		i.setIndex(2);
	} 
	
	private class InnerClass
	{
        private int index = 0;
        
        public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

    }
}