package net.sf.ahtutils.xml.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlTimeseriesTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTimeseriesTest.class);

	public AbstractXmlTimeseriesTest(Class<T> cXml)
	{
		super(cXml,"ts");
	}
}