package org.jeesl.model.xml.system.util;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlUtilsTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlUtilsTest.class);
	
	public AbstractXmlUtilsTest(Class<T> cXml)
	{
		super(cXml,"system/utils");
	}
}