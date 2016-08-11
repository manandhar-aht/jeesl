package org.jeesl.model.xml.report;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlReportTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlReportTest.class);
    
	public AbstractXmlReportTest(Class<T> cXml)
	{
		super(cXml,"report");
	}
}