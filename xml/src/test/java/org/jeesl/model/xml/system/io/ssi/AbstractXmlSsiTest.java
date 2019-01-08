package org.jeesl.model.xml.system.io.ssi;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSsiTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSsiTest.class);
	
	public AbstractXmlSsiTest(Class<T> cXml)
	{
		super(cXml,"system/io/ssi");
	}
}