package net.sf.ahtutils.xml.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlNavigationTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlNavigationTest.class);
	
	public AbstractXmlNavigationTest(Class<T> cXml)
	{
		super(cXml,"navigation");
	}
}