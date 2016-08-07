package net.sf.ahtutils.xml.project;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlProjectTest <T extends Object> extends AbstractXmlTest<T> 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlProjectTest.class);

	public AbstractXmlProjectTest(Class<T> cXml)
	{
		super(cXml,"project");
	}
}