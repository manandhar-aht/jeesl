package org.jeesl.factory.ejb.module.calendar;

import java.util.Date;
import java.util.TimeZone;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEjbTimeZoneFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestEjbTimeZoneFactory.class);
	
	private static String tzBerlin = "Europe/Berlin";
	
	
	@Test public void pre()
    {	
		TimeZone tz = TimeZone.getDefault();
		logger.debug(tz.getID());
		logger.debug(tz.getDisplayName());
		
		for(String s : TimeZone.getAvailableIDs())
		{
			logger.trace(s);
		}
    }
	
	@Test public void wrongId()
	{
		Assert.assertFalse(EjbTimeZoneFactory.supportedCode("Europe/Berlin222"));
		Assert.assertTrue(EjbTimeZoneFactory.supportedCode(tzBerlin));
	}
	
	@Test public void timezone()
	{
		Date dNow = new Date();
		Date dUtc = EjbTimeZoneFactory.toUtc(dNow,tzBerlin);
		
		logger.info(dNow.toString());
		logger.info(dUtc.toString());
	}
}