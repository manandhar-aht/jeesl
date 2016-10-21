package org.jeesl.model.xml.finance;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlFinanceTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlFinanceTest.class);
	
	public AbstractXmlFinanceTest(Class<T> cXml)
	{
		super(cXml,"finance");
	}
}