package org.jeesl.factory.ejb.module.calendar;

import java.util.Date;
import java.util.TimeZone;

import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.controller.processor.TimeZoneProcessor;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringUtil;

public class TestEjbTimeZoneFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestEjbTimeZoneFactory.class);
	
	@Before public void line(){logger.debug(StringUtil.stars());}
	
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
		Assert.assertTrue(EjbTimeZoneFactory.supportedCode(JeeslCalendarTimeZone.tzBerlin));
	}
	
	@Test public void timezone()
	{
		Date dNow = new Date();
		Date dUtc = EjbTimeZoneFactory.toUtc(dNow,JeeslCalendarTimeZone.tzBerlin);
		
		logger.info(dNow.toString());
		logger.info(dUtc.toString());
	}
	
	@Test public void utcToZone()
	{
		logger.debug("Projecting UTC to Berlin");
		Date dNow = new Date();
		Date dProject = TimeZoneProcessor.project(dNow,JeeslCalendarTimeZone.tzUtc,JeeslCalendarTimeZone.tzBerlin);
		
		logger.info("Now "+dNow.toString());
		logger.info("Prj "+dProject.toString());
	}
	
	@Test public void zoneToUtc()
	{
		logger.debug("Projecting Berlin To UTC");
		Date dNow = new Date();
		Date dProject = TimeZoneProcessor.project(dNow,JeeslCalendarTimeZone.tzBerlin,JeeslCalendarTimeZone.tzUtc);
		
		logger.debug("Now "+dNow.toString());
		logger.debug("Prj "+dProject.toString());
	}
}