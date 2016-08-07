package net.sf.ahtutils.xml.system;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSystemTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSystemTest.class);
	
	public AbstractXmlSystemTest(Class<T> cXml)
	{
		super(cXml,"system");
	}
}