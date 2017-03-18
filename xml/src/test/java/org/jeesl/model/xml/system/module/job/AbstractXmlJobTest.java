package org.jeesl.model.xml.system.module.job;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlJobTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlJobTest.class);

	public AbstractXmlJobTest(Class<T> cXml)
	{
		super(cXml,"system/job");
	}
}