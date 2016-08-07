package org.jeesl.model.xml.status;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlStatusTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlStatusTest.class);
	
	public AbstractXmlStatusTest(java.lang.Class<T> cXml)
	{
		super(cXml,"status");
	}
}