package org.jeesl.model.xml.system.io.db;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlDbseedTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlDbseedTest.class);
	
	public AbstractXmlDbseedTest(Class<T> cXml)
	{
		super(cXml,"dbseed");
	}
}