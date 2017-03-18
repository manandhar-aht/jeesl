package org.jeesl.model.xml.dev.srs;

import org.jeesl.AbstractXmlTest;
import org.jeesl.model.xml.system.core.AbstractXmlSystemTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSrsTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlSrsTest(Class<T> cXml)
	{
		super(cXml,"dev/srs");
	}
}