package org.jeesl.model.xml.ts;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlTimeseriesTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTimeseriesTest.class);

	public AbstractXmlTimeseriesTest(Class<T> cXml)
	{
		super(cXml,"ts");
	}
}