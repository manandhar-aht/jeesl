package org.jeesl.model.xml.symbol;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSymbolTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSymbolTest.class);

	public AbstractXmlSymbolTest(Class<T> cXml)
	{
		super(cXml,"symbol");
	}
}