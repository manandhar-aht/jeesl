package org.jeesl.model.xml.text;

import net.sf.ahtutils.xml.system.AbstractXmlSystemTest;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlTextTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlTextTest(Class<T> cXml)
	{
		super(cXml,"text");
	}
}