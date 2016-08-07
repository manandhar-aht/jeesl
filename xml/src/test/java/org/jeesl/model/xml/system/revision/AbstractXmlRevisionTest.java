package org.jeesl.model.xml.system.revision;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlRevisionTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlRevisionTest.class);
	
	public AbstractXmlRevisionTest(Class<T> cXml)
	{
		super(cXml,"system/revision");
	}
}