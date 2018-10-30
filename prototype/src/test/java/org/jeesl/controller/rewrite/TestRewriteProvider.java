package org.jeesl.controller.rewrite;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRewriteProvider 
{
	final static Logger logger = LoggerFactory.getLogger(TestRewriteProvider.class);
		
	@BeforeClass
    public static void initLogger()
	{
		
    }

	@Test
	public void testUrlMapping()
	{
		Assert.assertEquals("/data", AbstractRewriteProvider.getUrlMapping("ww", "http://xx.yyy.zzz:8080/ww/data"));
		Assert.assertEquals("/data", AbstractRewriteProvider.getUrlMapping("xx", "http://xx.yyy.zzz:8080/xx/data?xxxx"));
		Assert.assertEquals("/data", AbstractRewriteProvider.getUrlMapping("xx", "http://xx.yyy.zzz:8080/xx/data"));
	}
}