package org.jeesl.model.xml.jeesl;

import org.jeesl.AbstractXmlTest;
import org.jeesl.model.xml.system.core.AbstractXmlSystemTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlAhtTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlAhtTest(Class<T> cXml)
	{
		super(cXml,"aht");
	}
}