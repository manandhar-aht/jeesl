package org.jeesl.model.xml.mail;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlMailTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlMailTest.class);
	
	public AbstractXmlMailTest(Class<T> cXml)
	{
		super(cXml,"mail");
	}
}