package org.jeesl.model.xml.module.calendar;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlCalendarTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlCalendarTest.class);

	public AbstractXmlCalendarTest(Class<T> cXml)
	{
		super(cXml,"module/calendar");
	}
}