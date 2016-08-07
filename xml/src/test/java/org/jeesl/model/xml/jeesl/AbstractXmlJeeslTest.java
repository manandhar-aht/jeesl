package org.jeesl.model.xml.jeesl;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.system.AbstractXmlSystemTest;

public abstract class AbstractXmlJeeslTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlJeeslTest(Class<T> cXml)
	{
		super(cXml,"aht");
	}
}