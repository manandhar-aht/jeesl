package org.jeesl.model.xml.system.io.sync;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSyncTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSyncTest.class);
	
	public AbstractXmlSyncTest(Class<T> cXml)
	{
		super(cXml,"sync");
	}
}